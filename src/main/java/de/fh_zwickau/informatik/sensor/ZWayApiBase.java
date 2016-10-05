/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fh_zwickau.informatik.sensor.model.devicehistory.DeviceHistory;
import de.fh_zwickau.informatik.sensor.model.devicehistory.DeviceHistoryList;
import de.fh_zwickau.informatik.sensor.model.devices.Device;
import de.fh_zwickau.informatik.sensor.model.devices.DeviceCommand;
import de.fh_zwickau.informatik.sensor.model.devices.DeviceList;
import de.fh_zwickau.informatik.sensor.model.devices.IDeviceCommands;
import de.fh_zwickau.informatik.sensor.model.instances.Instance;
import de.fh_zwickau.informatik.sensor.model.instances.InstanceList;
import de.fh_zwickau.informatik.sensor.model.locations.Location;
import de.fh_zwickau.informatik.sensor.model.locations.LocationList;
import de.fh_zwickau.informatik.sensor.model.modules.ModuleList;
import de.fh_zwickau.informatik.sensor.model.namespaces.NamespaceList;
import de.fh_zwickau.informatik.sensor.model.notifications.Notification;
import de.fh_zwickau.informatik.sensor.model.notifications.NotificationList;
import de.fh_zwickau.informatik.sensor.model.profiles.Profile;
import de.fh_zwickau.informatik.sensor.model.profiles.ProfileList;
import de.fh_zwickau.informatik.sensor.model.zwaveapi.controller.ZWaveController;
import de.fh_zwickau.informatik.sensor.model.zwaveapi.devices.ZWaveDevice;

/**
 * The {@link ZWayApiBase} implements some basic methods of ZAutomation API. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public abstract class ZWayApiBase implements IZWayApi, IDeviceCommands {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    protected String mIpAddress;
    protected Integer mPort;
    protected String mProtocol;
    protected String mUsername;
    protected String mPassword;

    protected IZWayApiCallbacks mCaller;

    /**
     * Usage of Z-Way Session Id
     *
     * Response response = client.target(mConfig.getTopLevelUrl() + ZWAY_ZAUTOMATION_PATH_STATUS)
     * .request(MediaType.TEXT_PLAIN_TYPE).cookie("ZWAYSession", zwaySessionId).get();
     */
    protected String mZWaySessionId;

    /**
     * Setup the Z-Way API with passed values.
     *
     * @param ipAddress ip address
     * @param port port number
     * @param protocol protocol
     * @param username username
     * @param password password
     * @param caller receive callbacks
     */
    public ZWayApiBase(String ipAddress, Integer port, String protocol, String username, String password,
            IZWayApiCallbacks caller) {
        mIpAddress = ipAddress;
        mPort = port;
        mProtocol = protocol;
        mUsername = username;
        mPassword = password;

        logger.debug("ZWayApi initialized: " + getTopLevelUrl() + " with: " + mUsername + "/" + mPassword);

        mCaller = caller;

        mZWaySessionId = null;
    }

    /**
     * Checks session id and try login again if session id is null.
     *
     * @return - true if authenticated and false if also another try failed.
     */
    protected boolean checkLogin() {
        logger.debug("Check login");
        if (mZWaySessionId == null) {
            logger.debug("Session id is null, retry login");
            if (getLogin() == null) {
                logger.debug("Login failed");
                mCaller.authenticationError();
                return false;
            } else {
                logger.debug("Successfully logged in");
                return true;
            }
        } else {
            return true;
        }
    }

    /*****************************
     ****** Utility Methods ******
     ****************************/

    protected String getZAutomationTopLevelUrl() {
        return getTopLevelUrl() + PATH + VERSION;
    }

    protected String getZWaveTopLevelUrl() {
        return getTopLevelUrl() + ZWAVE_PATH;
    }

    protected String getTopLevelUrl() {
        return mProtocol + "://" + mIpAddress + ":" + mPort;
    }

    /**
     * Handle HTTP status codes and invoke the appropriate callback method.
     *
     * @param status - HTTP status code
     */
    protected void processResponseStatus(int status) {
        logger.debug("Processing response status: " + String.valueOf(status));

        switch (status) {
            case 201:
                mCaller.httpStatusError(status, "Created", false);
                break;
            case 204:
                mCaller.httpStatusError(status, "No content", false);
                break;
            case 304:
                mCaller.httpStatusError(status, "Not modified", false);
                break;
            case 400:
                mCaller.httpStatusError(status, "Bad request", false);
                break;
            case 401:
                mCaller.httpStatusError(status, "Unauthorized", false);
                break;
            case 403:
                mCaller.httpStatusError(status, "Forbidden", false);
                break;
            case 404:
                mCaller.httpStatusError(status, "Not found", false);
                break;
            case 405:
                mCaller.httpStatusError(status, "Mehtod not allowed", false);
                break;
            case 500:
                mCaller.httpStatusError(status, "Internal server error", false);
                break;
            case 501:
                mCaller.httpStatusError(status, "Not implemented", false);
                break;
            case 503:
                mCaller.httpStatusError(status, "Service unavailable", false);
                break;
            default:
                mCaller.httpStatusError(status, "The connection to the Z-Way Server cannot be established", true);
                break;
        }
    }

    /**************************************
     ****** DeviceCommands Interface ******
     *************************************/

    @Override
    public String on(Device device) {
        return getDeviceCommand(new DeviceCommand(device.getDeviceId(), "on", null));
    }

    @Override
    public String off(Device device) {
        return getDeviceCommand(new DeviceCommand(device.getDeviceId(), "off", null));
    }

    @Override
    public String update(Device device) {
        return getDeviceCommand(new DeviceCommand(device.getDeviceId(), "update", null));
    }

    @Override
    public String min(Device device) {
        return getDeviceCommand(new DeviceCommand(device.getDeviceId(), "min", null));
    }

    @Override
    public String max(Device device) {
        return getDeviceCommand(new DeviceCommand(device.getDeviceId(), "max", null));
    }

    @Override
    public String exact(Device device, String level) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("level", level);
        return getDeviceCommand(new DeviceCommand(device.getDeviceId(), "exact", params));
    }

    @Override
    public String exact(Device device, Integer red, Integer green, Integer blue) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("red", red.toString());
        params.put("green", green.toString());
        params.put("blue", blue.toString());
        return getDeviceCommand(new DeviceCommand(device.getDeviceId(), "exact", params));
    }

    @Override
    public String exactSmooth(Device device, String level, Integer duration) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("level", level);
        params.put("duration", duration.toString());
        return getDeviceCommand(new DeviceCommand(device.getDeviceId(), "exact", params));
    }

    @Override
    public String up(Device device) {
        return getDeviceCommand(new DeviceCommand(device.getDeviceId(), "up", null));
    }

    @Override
    public String down(Device device) {
        return getDeviceCommand(new DeviceCommand(device.getDeviceId(), "down", null));
    }

    @Override
    public String upMax(Device device) {
        return getDeviceCommand(new DeviceCommand(device.getDeviceId(), "upMax", null));
    }

    @Override
    public String startUp(Device device) {
        return getDeviceCommand(new DeviceCommand(device.getDeviceId(), "startUp", null));
    }

    @Override
    public String startDown(Device device) {
        return getDeviceCommand(new DeviceCommand(device.getDeviceId(), "startDown", null));
    }

    @Override
    public String stop(Device device) {
        return getDeviceCommand(new DeviceCommand(device.getDeviceId(), "stop", null));
    }

    @Override
    public String open(Device device) {
        return getDeviceCommand(new DeviceCommand(device.getDeviceId(), "open", null));
    }

    @Override
    public String close(Device device) {
        return getDeviceCommand(new DeviceCommand(device.getDeviceId(), "close", null));
    }

    @Override
    public String setTemp(Device device, Double temp) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("temp", temp.toString());
        return getDeviceCommand(new DeviceCommand(device.getDeviceId(), "setTemp", params));
    }

    @Override
    public String setMode(Device device, String mode) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("mode", mode);
        return getDeviceCommand(new DeviceCommand(device.getDeviceId(), "setMode", params));
    }

    @Override
    public String increase(Device device) {
        return getDeviceCommand(new DeviceCommand(device.getDeviceId(), "increase", null));
    }

    @Override
    public String decrease(Device device) {
        return getDeviceCommand(new DeviceCommand(device.getDeviceId(), "decrease", null));
    }

    @Override
    public String upstart(Device device) {
        return getDeviceCommand(new DeviceCommand(device.getDeviceId(), "upstart", null));
    }

    @Override
    public String upstop(Device device) {
        return getDeviceCommand(new DeviceCommand(device.getDeviceId(), "upstop", null));
    }

    @Override
    public String downstart(Device device) {
        return getDeviceCommand(new DeviceCommand(device.getDeviceId(), "downstart", null));
    }

    @Override
    public String downstop(Device device) {
        return getDeviceCommand(new DeviceCommand(device.getDeviceId(), "downstop", null));
    }

    /*********************************
     ****** Z-Way API Interface ******
     ********************************/

    @Override
    public String getStatus() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean getRestart() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getLogin() {
        throw new UnsupportedOperationException();
    }

    @Override
    public NamespaceList getNamespaces() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ModuleList getModules() {
        throw new UnsupportedOperationException();
    }

    @Override
    public InstanceList getInstances() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Instance postInstance(Instance instance) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Instance getInstance(String instanceId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Instance putInstance(Instance instance) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean deleteInstance(String instanceId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public DeviceList getDevices() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Device postDevice(Device device) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Device getDevice(String deviceId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getDeviceCommand(DeviceCommand command) {
        throw new UnsupportedOperationException();
    }

    @Override
    public LocationList getLocations() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Location postLocation(Location location) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Location getLocation(String locationId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Location putLocation(Location location) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean deleteLocation(String locationId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ProfileList getProfiles() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Profile postProfile(Profile profile) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Profile getProfile(String profileId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Profile putProfile(Profile profile) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean deleteProfile(String profileId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public NotificationList getNotifications() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Notification getNotification(String notificationId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Notification putNotification(Notification notification) {
        throw new UnsupportedOperationException();
    }

    @Override
    public DeviceHistoryList getDeviceHistories() {
        throw new UnsupportedOperationException();
    }

    @Override
    public DeviceHistory getDeviceHistory(String deviceId, Date since) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ZWaveDevice getZWaveDevice(int nodeId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ZWaveController getZWaveController() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getZWaveInclusion(int flag) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getZWaveExclusion(int flag) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getStatus(IZWayCallback<String> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getRestart(IZWayCallback<Boolean> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getNamespaces(IZWayCallback<NamespaceList> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getModules(IZWayCallback<ModuleList> callback) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void getInstances(IZWayCallback<InstanceList> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void postInstance(Instance instance, IZWayCallback<Instance> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getInstance(String instanceId, IZWayCallback<Instance> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putInstance(Instance instance, IZWayCallback<Instance> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteInstance(String instanceId, IZWayCallback<Boolean> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getDevices(IZWayCallback<DeviceList> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void postDevice(Device device, IZWayCallback<Device> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getDevice(String deviceId, IZWayCallback<Device> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getDeviceCommand(DeviceCommand command, IZWayCallback<String> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getLocations(IZWayCallback<LocationList> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void postLocation(Location location, IZWayCallback<Location> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getLocation(String locationId, IZWayCallback<Location> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putLocation(Location location, IZWayCallback<Location> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteLocation(String locationId, IZWayCallback<Boolean> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getProfiles(IZWayCallback<ProfileList> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void postProfile(Profile profile, IZWayCallback<Profile> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getProfile(String profileId, IZWayCallback<Profile> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putProfile(Profile profile, IZWayCallback<Profile> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteProfile(String profileId, IZWayCallback<Boolean> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getNotifications(IZWayCallback<NotificationList> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getNotification(String notificationId, IZWayCallback<Notification> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putNotification(Notification notification, IZWayCallback<Notification> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getDeviceHistories(IZWayCallback<DeviceHistoryList> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getDeviceHistory(String deviceId, Date since, IZWayCallback<DeviceHistory> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getZWaveDevice(int nodeId, IZWayCallback<ZWaveDevice> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getZWaveController(IZWayCallback<ZWaveController> callback) {
        throw new UnsupportedOperationException();
    }
}
