/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fh_zwickau.informatik.sensor.model.devicehistory.DeviceHistoryData;
import de.fh_zwickau.informatik.sensor.model.devicehistory.DeviceHistoryList;
import de.fh_zwickau.informatik.sensor.model.devices.Device;
import de.fh_zwickau.informatik.sensor.model.devices.DeviceCommand;
import de.fh_zwickau.informatik.sensor.model.devices.DeviceList;
import de.fh_zwickau.informatik.sensor.model.devices.IDeviceCommands;
import de.fh_zwickau.informatik.sensor.model.icons.IconList;
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
import de.fh_zwickau.informatik.sensor.model.system.SystemInfo;
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

    protected Integer mRemoteId;
    protected Boolean mUseRemoteService;

    protected IZWayApiCallbacks mCaller;

    /**
     * Usage of Z-Way Session Id for local connection
     *
     * Response response = client.target(mConfig.getTopLevelUrl() + ZWAY_ZAUTOMATION_PATH_STATUS)
     * .request(MediaType.TEXT_PLAIN_TYPE).cookie("ZWAYSession", zwaySessionId).get();
     *
     * Contains Session Id for local or remote connection depending on remote usage flag.
     */
    protected String mZWaySessionId;
    protected String mZWayRemoteSessionId;

    /**
     * Setup the Z-Way API with passed values.
     *
     * @param ipAddress ip address
     * @param port port number
     * @param protocol protocol
     * @param username username
     * @param password password
     * @param remoteId remote id
     * @param useRemoteService
     * @param caller receive callbacks
     */
    public ZWayApiBase(String ipAddress, Integer port, String protocol, String username, String password,
            Integer remoteId, Boolean useRemoteService, IZWayApiCallbacks caller) {
        mIpAddress = ipAddress;
        mPort = port;
        mProtocol = protocol;
        mUsername = username;
        mPassword = password;

        mRemoteId = remoteId;
        mUseRemoteService = useRemoteService;

        if (mUseRemoteService) {
            logger.debug("ZWayApi for remote connection initialized: " + getTopLevelUrl() + " with: " + mUsername + "/"
                    + mPassword);
        } else {
            logger.debug("ZWayApi for local connection initialized: " + getTopLevelUrl() + " with: " + mUsername + "/"
                    + mPassword);
        }

        mCaller = caller;

        mZWaySessionId = null;
        mZWayRemoteSessionId = null;
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

    @Override
    public String getTopLevelUrl() {
        // Method has public visibility for camera stream (http://localhost:8083/DEVICE_ID/stream)
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

    protected void handleException(Exception e, String request) {
        if (e.getMessage() != null) {
            logger.warn("Request {} failed: {}", request, e.getMessage());
            mCaller.apiError(e.getMessage(), false);
        } else if (e.getCause() != null && e.getCause().getMessage() != null) {
            logger.warn("Request {} failed: {}", request, e.getCause().getMessage());
            mCaller.apiError(e.getCause().getMessage(), false);
        } else {
            logger.warn("Request {} failed: unexpected exception", request);
            mCaller.apiError("Unexpected exception", false);
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

    // Camera

    @Override
    public String zoomIn(Device device) {
        return getDeviceCommand(new DeviceCommand(device.getDeviceId(), "zoomIn", null));
    }

    @Override
    public String zoomOut(Device device) {
        return getDeviceCommand(new DeviceCommand(device.getDeviceId(), "zoomOut", null));
    }

    @Override
    public String left(Device device) {
        return getDeviceCommand(new DeviceCommand(device.getDeviceId(), "left", null));
    }

    @Override
    public String right(Device device) {
        return getDeviceCommand(new DeviceCommand(device.getDeviceId(), "right", null));
    }

    /*********************************
     ****** Z-Way API Interface ******
     ********************************/

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getStatus()
     */
    @Override
    public String getStatus() {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getRestart()
     */
    @Override
    public Boolean getRestart() {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getLogin()
     */
    @Override
    public String getLogin() {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getCurrentProfile()
     */
    @Override
    public Profile getCurrentProfile() {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getNamespaces()
     */
    @Override
    public NamespaceList getNamespaces() {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getModules()
     */
    @Override
    public ModuleList getModules() {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getInstances()
     */
    @Override
    public InstanceList getInstances() {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.fh_zwickau.informatik.sensor.IZWayApi#postInstance(de.fh_zwickau.informatik.sensor.model.instances.Instance)
     */
    @Override
    public Instance postInstance(Instance instance) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getInstance(java.lang.Integer)
     */
    @Override
    public Instance getInstance(Integer instanceId) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.fh_zwickau.informatik.sensor.IZWayApi#putInstance(de.fh_zwickau.informatik.sensor.model.instances.Instance)
     */
    @Override
    public Instance putInstance(Instance instance) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#deleteInstance(java.lang.Integer)
     */
    @Override
    public Boolean deleteInstance(Integer instanceId) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getDevices()
     */
    @Override
    public DeviceList getDevices() {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#putDevice(de.fh_zwickau.informatik.sensor.model.devices.Device)
     */
    @Override
    public Device putDevice(Device device) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getDevice(java.lang.String)
     */
    @Override
    public Device getDevice(String deviceId) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getDeviceAsJson(java.lang.String)
     */
    @Override
    public String getDeviceAsJson(String deviceId) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getDeviceCommand(de.fh_zwickau.informatik.sensor.model.devices.
     * DeviceCommand)
     */
    @Override
    public String getDeviceCommand(DeviceCommand command) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getLocations()
     */
    @Override
    public LocationList getLocations() {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.fh_zwickau.informatik.sensor.IZWayApi#postLocation(de.fh_zwickau.informatik.sensor.model.locations.Location)
     */
    @Override
    public Location postLocation(Location location) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getLocation(java.lang.String)
     */
    @Override
    public Location getLocation(String locationId) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.fh_zwickau.informatik.sensor.IZWayApi#putLocation(de.fh_zwickau.informatik.sensor.model.locations.Location)
     */
    @Override
    public Location putLocation(Location location) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#deleteLocation(java.lang.String)
     */
    @Override
    public Boolean deleteLocation(String locationId) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getProfiles()
     */
    @Override
    public ProfileList getProfiles() {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#postProfile(de.fh_zwickau.informatik.sensor.model.profiles.Profile)
     */
    @Override
    public Profile postProfile(Profile profile) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getProfile(java.lang.String)
     */
    @Override
    public Profile getProfile(String profileId) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#putProfile(de.fh_zwickau.informatik.sensor.model.profiles.Profile)
     */
    @Override
    public Profile putProfile(Profile profile) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#deleteProfile(java.lang.String)
     */
    @Override
    public Boolean deleteProfile(String profileId) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getNotifications(java.lang.Long)
     */
    @Override
    public NotificationList getNotifications(Long since) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getNotification(java.lang.Long)
     */
    @Override
    public Notification getNotification(Long notificationId) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.fh_zwickau.informatik.sensor.IZWayApi#putNotification(de.fh_zwickau.informatik.sensor.model.notifications.
     * Notification)
     */
    @Override
    public Notification putNotification(Notification notification) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getDeviceHistories()
     */
    @Override
    public DeviceHistoryList getDeviceHistories() {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getDeviceHistory(java.lang.String, java.lang.Long)
     */
    @Override
    public ArrayList<DeviceHistoryData> getDeviceHistory(String deviceId, Long since) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getIcons()
     */
    @Override
    public IconList getIcons() {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#postIcon(java.io.File)
     */
    @Override
    public String postIcon(File image) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getSystemInfo()
     */
    @Override
    public SystemInfo getSystemInfo() {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getZWaveDevice(int)
     */
    @Override
    public ZWaveDevice getZWaveDevice(int nodeId) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getZWaveController()
     */
    @Override
    public ZWaveController getZWaveController() {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getZWaveInclusion(int)
     */
    @Override
    public void getZWaveInclusion(int flag) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getZWaveExclusion(int)
     */
    @Override
    public void getZWaveExclusion(int flag) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#updateControllerData(java.lang.String, java.lang.String)
     */
    @Override
    public void updateControllerData(String field, String value) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getStatus(de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void getStatus(IZWayCallback<String> callback) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getRestart(de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void getRestart(IZWayCallback<Boolean> callback) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getNamespaces(de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void getNamespaces(IZWayCallback<NamespaceList> callback) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getModules(de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void getModules(IZWayCallback<ModuleList> callback) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getInstances(de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void getInstances(IZWayCallback<InstanceList> callback) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.fh_zwickau.informatik.sensor.IZWayApi#postInstance(de.fh_zwickau.informatik.sensor.model.instances.Instance,
     * de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void postInstance(Instance instance, IZWayCallback<Instance> callback) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getInstance(java.lang.Integer,
     * de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void getInstance(Integer instanceId, IZWayCallback<Instance> callback) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.fh_zwickau.informatik.sensor.IZWayApi#putInstance(de.fh_zwickau.informatik.sensor.model.instances.Instance,
     * de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void putInstance(Instance instance, IZWayCallback<Instance> callback) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#deleteInstance(java.lang.Integer,
     * de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void deleteInstance(Integer instanceId, IZWayCallback<Boolean> callback) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getDevices(de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void getDevices(IZWayCallback<DeviceList> callback) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#putDevice(de.fh_zwickau.informatik.sensor.model.devices.Device,
     * de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void putDevice(Device device, IZWayCallback<Device> callback) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getDevice(java.lang.String,
     * de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void getDevice(String deviceId, IZWayCallback<Device> callback) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getDeviceAsJson(java.lang.String,
     * de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void getDeviceAsJson(String deviceId, IZWayCallback<String> callback) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getDeviceCommand(de.fh_zwickau.informatik.sensor.model.devices.
     * DeviceCommand, de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void getDeviceCommand(DeviceCommand command, IZWayCallback<String> callback) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getLocations(de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void getLocations(IZWayCallback<LocationList> callback) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.fh_zwickau.informatik.sensor.IZWayApi#postLocation(de.fh_zwickau.informatik.sensor.model.locations.Location,
     * de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void postLocation(Location location, IZWayCallback<Location> callback) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getLocation(java.lang.String,
     * de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void getLocation(String locationId, IZWayCallback<Location> callback) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.fh_zwickau.informatik.sensor.IZWayApi#putLocation(de.fh_zwickau.informatik.sensor.model.locations.Location,
     * de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void putLocation(Location location, IZWayCallback<Location> callback) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#deleteLocation(java.lang.String,
     * de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void deleteLocation(String locationId, IZWayCallback<Boolean> callback) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getProfiles(de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void getProfiles(IZWayCallback<ProfileList> callback) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#postProfile(de.fh_zwickau.informatik.sensor.model.profiles.Profile,
     * de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void postProfile(Profile profile, IZWayCallback<Profile> callback) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getProfile(java.lang.String,
     * de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void getProfile(String profileId, IZWayCallback<Profile> callback) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#putProfile(de.fh_zwickau.informatik.sensor.model.profiles.Profile,
     * de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void putProfile(Profile profile, IZWayCallback<Profile> callback) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#deleteProfile(java.lang.String,
     * de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void deleteProfile(String profileId, IZWayCallback<Boolean> callback) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getNotifications(de.fh_zwickau.informatik.sensor.IZWayCallback,
     * java.lang.Long)
     */
    @Override
    public void getNotifications(IZWayCallback<NotificationList> callback, Long since) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getNotification(java.lang.Long,
     * de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void getNotification(Long notificationId, IZWayCallback<Notification> callback) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.fh_zwickau.informatik.sensor.IZWayApi#putNotification(de.fh_zwickau.informatik.sensor.model.notifications.
     * Notification, de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void putNotification(Notification notification, IZWayCallback<Notification> callback) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getDeviceHistories(de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void getDeviceHistories(IZWayCallback<DeviceHistoryList> callback) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getDeviceHistory(java.lang.String, java.lang.Long,
     * de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void getDeviceHistory(String deviceId, Long since, IZWayCallback<ArrayList<DeviceHistoryData>> callback) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getIcons(de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void getIcons(IZWayCallback<IconList> callback) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getSystemInfo(de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void getSystemInfo(IZWayCallback<SystemInfo> callback) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getZWaveDevice(int, de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void getZWaveDevice(int nodeId, IZWayCallback<ZWaveDevice> callback) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getZWaveController(de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void getZWaveController(IZWayCallback<ZWaveController> callback) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getZWaveDeviceThermostatModeSet(int, int)
     */
    @Override
    public void getZWaveDeviceThermostatModeSet(int nodeId, int mode) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getZWaySessionId()
     */
    @Override
    public String getZWaySessionId() {
        return mZWaySessionId;
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#getZWayRemoteSessionId()
     */
    @Override
    public String getZWayRemoteSessionId() {
        return mZWayRemoteSessionId;
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.IZWayApi#cancelCurrentRequest()
     */
    @Override
    public void cancelCurrentRequest() {
        throw new UnsupportedOperationException();
    }
}
