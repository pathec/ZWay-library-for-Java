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
import java.util.Date;

import de.fh_zwickau.informatik.sensor.model.devicehistory.DeviceHistory;
import de.fh_zwickau.informatik.sensor.model.devicehistory.DeviceHistoryList;
import de.fh_zwickau.informatik.sensor.model.devices.Device;
import de.fh_zwickau.informatik.sensor.model.devices.DeviceCommand;
import de.fh_zwickau.informatik.sensor.model.devices.DeviceList;
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
import de.fh_zwickau.informatik.sensor.model.zwaveapi.controller.ZWaveController;
import de.fh_zwickau.informatik.sensor.model.zwaveapi.devices.ZWaveDevice;

/**
 * The {@link IZWayApi} represents the ZAutomation API (and ZWaveAPI). See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public interface IZWayApi {
    static String VERSION = "/v1";
    static String PATH = "/ZAutomation/api";
    static String ZWAVE_PATH = "/ZWaveAPI/Run";

    // Platform Availability
    /**
     * @return Z-Way server status
     */
    String getStatus();

    /**
     * Asynchronous version of {@link #getStatus()}
     *
     * @param callback contains the same data like synchronous return value
     */
    void getStatus(IZWayCallback<String> callback);

    // Platform Restart
    /**
     * @return true if successfully performed
     */
    Boolean getRestart();

    /**
     * Asynchronous version of {@link #getRestart()}
     *
     * @param callback contains the same data like synchronous return value
     */
    void getRestart(IZWayCallback<Boolean> callback);

    // Login
    /**
     * @return Z-Way session id
     */
    String getLogin();

    // no asynchronous version for login call

    // Current profile
    /**
     * @return ZAutomation profile
     */
    Profile getCurrentProfile();

    // Namespaces
    /**
     * @return ZAutomation namespace list
     */
    NamespaceList getNamespaces();

    /**
     * Asynchronous version of {@link #getNamespaces()}
     *
     * @param callback contains the same data like synchronous return value
     */
    void getNamespaces(IZWayCallback<NamespaceList> callback);

    // Instances and Modules
    /**
     * @return ZAutomation module list
     */
    ModuleList getModules();

    /**
     * Asynchronous version of {@link #getModules()}
     *
     * @param callback contains the same data like synchronous return value
     */
    void getModules(IZWayCallback<ModuleList> callback);

    /**
     * @return ZAutomation instance list
     */
    InstanceList getInstances();

    /**
     * Asynchronous version of {@link #getInstances()}
     *
     * @param callback contains the same data like synchronous return value
     */
    void getInstances(IZWayCallback<InstanceList> callback);

    /**
     * @param instance ZAutomation instance
     * @return new ZAutomation instance
     */
    Instance postInstance(Instance instance);

    /**
     * Asynchronous version of {@link #postInstance(Instance)}
     *
     * @param instance ZAutomation instance
     * @param callback contains the same data like synchronous return value
     */
    void postInstance(Instance instance, IZWayCallback<Instance> callback);

    /**
     * @param instanceId ZAutomation instance id
     * @return ZAutomation instance
     */
    Instance getInstance(Integer instanceId);

    /**
     * Asynchronous version of {@link #getInstance(String)}
     *
     * @param instanceId ZAutomation instance id
     * @param callback contains the same data like synchronous return value
     */
    void getInstance(Integer instanceId, IZWayCallback<Instance> callback);

    /**
     * @param instance ZAutomation instance
     * @return updated ZAutomation instance
     */
    Instance putInstance(Instance instance);

    /**
     * Asynchronous version of {@link #putInstance(Instance)}
     *
     * @param instance ZAutomation instance
     * @param callback contains the same data like synchronous return value
     */
    void putInstance(Instance instance, IZWayCallback<Instance> callback);

    /**
     * @param instanceId ZAutomation instance id
     * @return true if ZAutomation instance deleted
     */
    Boolean deleteInstance(Integer instanceId);

    /**
     * Asynchronous version of {@link #deleteInstance(String)}
     *
     * @param instanceId ZAutomation instance id
     * @param callback contains the same data like synchronous return value
     */
    void deleteInstance(Integer instanceId, IZWayCallback<Boolean> callback);

    // Devices

    /**
     * @return ZAutomation device list
     */
    DeviceList getDevices();

    /**
     * Asynchronous version of {@link #getDevices()}
     *
     * @param callback contains the same data like synchronous return value
     */
    void getDevices(IZWayCallback<DeviceList> callback);

    /**
     * @param device ZAutomation device
     * @return new ZAutomation device
     */
    Device putDevice(Device device);

    /**
     * Asynchronous version of {@link #postDevice(Device)}
     *
     * @param device ZAutomation device
     * @param callback contains the same data like synchronous return value
     */
    void putDevice(Device device, IZWayCallback<Device> callback);

    /**
     * @param deviceId ZAutomation device id
     * @return ZAutomation device
     */
    Device getDevice(String deviceId);

    /**
     * Asynchronous version of {@link #getDevice(String)}
     *
     * @param deviceId ZAutomation device id
     * @param callback contains the same data like synchronous return value
     */
    void getDevice(String deviceId, IZWayCallback<Device> callback);

    /**
     * @param command ZAutomation command
     * @return response message
     */
    String getDeviceCommand(DeviceCommand command);

    /**
     * Asynchronous version of {@link #getDeviceCommand(DeviceCommand)}
     *
     * @param command ZAutomation command
     * @param callback contains the same data like synchronous return value
     */
    void getDeviceCommand(DeviceCommand command, IZWayCallback<String> callback);

    // Locations

    /**
     * @return ZAutomation location list
     */
    LocationList getLocations();

    /**
     * Asynchronous version of {@link #getLocations()}
     *
     * @param callback contains the same data like synchronous return value
     */
    void getLocations(IZWayCallback<LocationList> callback);

    /**
     * @param location ZAutomation location id
     * @return new ZAutomation location id
     */
    Location postLocation(Location location);

    /**
     * Asynchronous version of {@link #postLocation(Location)}
     *
     * @param location ZAutomation location
     * @param callback contains the same data like synchronous return value
     */
    void postLocation(Location location, IZWayCallback<Location> callback);

    /**
     * @param locationId ZAutomation location id
     * @return ZAutomation location
     */
    Location getLocation(String locationId);

    /**
     * Asynchronous version of {@link #getLocation(String)}
     *
     * @param locationId ZAutomation location id
     * @param callback contains the same data like synchronous return value
     */
    void getLocation(String locationId, IZWayCallback<Location> callback);

    /**
     * @param location ZAutomation location
     * @return updated ZAutomation location
     */
    Location putLocation(Location location);

    /**
     * Asynchronous version of {@link #putLocation(Location)}
     *
     * @param location ZAutomation location
     * @param callback contains the same data like synchronous return value
     */
    void putLocation(Location location, IZWayCallback<Location> callback);

    /**
     * @param locationId ZAutomation location id
     * @return true if ZAutomation location deleted
     */
    Boolean deleteLocation(String locationId);

    /**
     * Asynchronous version of {@link #deleteLocation(String)}
     *
     * @param locationId ZAutomation location id
     * @param callback contains the same data like synchronous return value
     */
    void deleteLocation(String locationId, IZWayCallback<Boolean> callback);

    // Profiles

    /**
     * @return ZAutomation profile list
     */
    ProfileList getProfiles();

    /**
     * Asynchronous version of {@link #getProfiles()}
     *
     * @param callback contains the same data like synchronous return value
     */
    void getProfiles(IZWayCallback<ProfileList> callback);

    /**
     * @param profile ZAutomation profile
     * @return new ZAutomation profile
     */
    Profile postProfile(Profile profile);

    /**
     * Asynchronous version of {@link #postProfile(Profile)}
     *
     * @param profile ZAutomation profile
     * @param callback contains the same data like synchronous return value
     */
    void postProfile(Profile profile, IZWayCallback<Profile> callback);

    /**
     * @param profileId ZAutomation profile id
     * @return ZAutomation profile
     */
    Profile getProfile(String profileId);

    /**
     * Asynchronous version of {@link #getProfile(String)}
     *
     * @param profileId ZAutomation profile id
     * @param callback contains the same data like synchronous return value
     */
    void getProfile(String profileId, IZWayCallback<Profile> callback);

    /**
     * @param profile ZAutomation profile
     * @return updated ZAutomation profile
     */
    Profile putProfile(Profile profile);

    /**
     * Asynchronous version of {@link #putProfile(Profile)}
     *
     * @param profile ZAutomation profile
     * @param callback contains the same data like synchronous return value
     */
    void putProfile(Profile profile, IZWayCallback<Profile> callback);

    /**
     * @param profileId ZAutomation profile id
     * @return true if ZAutomation profile deleted
     */
    Boolean deleteProfile(String profileId);

    /**
     * Asynchronous version of {@link #deleteProfile(String)}
     *
     * @param profileId ZAutomation profile id
     * @param callback contains the same data like synchronous return value
     */
    void deleteProfile(String profileId, IZWayCallback<Boolean> callback);

    // Notifications

    /**
     * @param since UNIX timestamp
     * @return ZAutomation notification id
     */
    NotificationList getNotifications(Long since);

    /**
     * Asynchronous version of {@link #getNotifications()}
     *
     * @param callback contains the same data like synchronous return value
     * @param since UNIX timestamp
     */
    void getNotifications(IZWayCallback<NotificationList> callback, Long since);

    /**
     * @param notificationId ZAutomation notification id
     * @return ZAutomation notification
     */
    Notification getNotification(Long notificationId);

    /**
     * Asynchronous version of {@link #getNotification(String)}
     *
     * @param notificationId ZAutomation notification id
     * @param callback contains the same data like synchronous return value
     */
    void getNotification(Long notificationId, IZWayCallback<Notification> callback);

    /**
     * @param notification ZAutomation notification
     * @return updated ZAutomation notification
     */
    Notification putNotification(Notification notification);

    /**
     * Asynchronous version of {@link #putNotification(Notification)}
     *
     * @param notification ZAutomation notification
     * @param callback contains the same data like synchronous return value
     */
    void putNotification(Notification notification, IZWayCallback<Notification> callback);

    // Device History

    /**
     * @return ZAutomation device history list
     */
    DeviceHistoryList getDeviceHistories();

    /**
     * Asynchronous version of {@link #getDeviceHistories()}
     *
     * @param callback contains the same data like synchronous return value
     */
    void getDeviceHistories(IZWayCallback<DeviceHistoryList> callback);

    /**
     * @param deviceId ZAutomation device id
     * @param since lower limit
     * @return ZAutomation device history
     */
    DeviceHistory getDeviceHistory(String deviceId, Date since);

    /**
     * Asynchronous version of {@link #getDeviceHistory(String, Date)}
     *
     * @param deviceId ZAutomation device id
     * @param since lower limit
     * @param callback contains the same data like synchronous return value
     */
    void getDeviceHistory(String deviceId, Date since, IZWayCallback<DeviceHistory> callback);

    // Icons

    /**
     * @return ZAutomation icon list
     */
    IconList getIcons();

    /**
     * Asynchronous version of {@link #getIcons()}
     *
     * @param callback contains the same data like synchronous return value
     */
    void getIcons(IZWayCallback<IconList> callback);

    /**
     * @param image image file (recommended dimension 64px64px, as png or jpg and smaller than 30kB)
     * @return message
     */
    String postIcon(File image);

    /**********************
     ****** ZWaveAPI ******
     **********************/

    /**
     * @param nodeId Z-Wave device node id
     * @return Z-Wave device
     */
    ZWaveDevice getZWaveDevice(int nodeId);

    /**
     * Asynchronous version of {@link #getZWaveDevice(int)}
     *
     * @param nodeId Z-Wave device node id
     * @param callback contains the same data like synchronous return value
     */
    void getZWaveDevice(int nodeId, IZWayCallback<ZWaveDevice> callback);

    /**
     * @return Z-Wave controller
     */
    ZWaveController getZWaveController();

    /**
     * Asynchronous version of {@link #getZWaveController()}
     *
     * @param callback contains the same data like synchronous return value
     */
    void getZWaveController(IZWayCallback<ZWaveController> callback);

    /**
     * @param flag 1 start and 0 stop inclusion
     */
    void getZWaveInclusion(int flag);

    /**
     * @param flag 1 start and 0 stop exclusion
     */
    void getZWaveExclusion(int flag);

    /**
     * @param field of controller data
     * @param corresponding value
     */
    void updateControllerData(String field, String value);

    /**
     * @param nodeId Z-Wave device node id
     * @param mode thermostat mode
     */
    void getZWaveDeviceThermostatModeSet(int nodeId, int mode);

    String getZWaySessionId();

    String getZWayRemoteSessionId();

    void cancelCurrentRequest();
}
