/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor;

import de.fh_zwickau.informatik.sensor.model.devicehistory.DeviceHistory;
import de.fh_zwickau.informatik.sensor.model.devicehistory.DeviceHistoryList;
import de.fh_zwickau.informatik.sensor.model.devices.Device;
import de.fh_zwickau.informatik.sensor.model.devices.DeviceList;
import de.fh_zwickau.informatik.sensor.model.devices.zwaveapi.ZWaveDevice;
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

/**
 * The {@link IZWayApiCallbacks} defines a all possible callbacks of ZAutomation API. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public interface IZWayApiCallbacks {
    // Platform Availability

    /**
     * @param status
     */
    void getStatusResponse(String status);

    // Platform Restart

    /**
     * @param status
     */
    void getRestartResponse(Boolean status);

    // Login

    /**
     * @param sessionId
     */
    void getLoginResponse(String sessionId);

    // Namespaces

    /**
     * @param namespaces
     */
    void getNamespacesResponse(NamespaceList namespaces);

    // Instances and Modules

    /**
     * @param moduleList
     */
    void getModulesResponse(ModuleList moduleList);

    /**
     * @param instanceList
     */
    void getInstancesResponse(InstanceList instanceList);

    /**
     * @param instance
     */
    void postInstanceResponse(Instance instance);

    /**
     * @param instance
     */
    void getInstanceResponse(Instance instance);

    /**
     * @param instance
     */
    void putInstanceResponse(Instance instance);

    /**
     * @param status
     */
    void deleteInstanceResponse(boolean status);

    // Devices

    /**
     * @param deviceList
     */
    void getDevicesResponse(DeviceList deviceList);

    /**
     * @param device
     */
    void postDeviceResponse(Device device);

    /**
     * @param device
     */
    void getDeviceResponse(Device device);

    /**
     * @param message
     */
    void getDeviceCommandResponse(String message);

    // Locations

    /**
     * @param locationList
     */
    void getLocationsResponse(LocationList locationList);

    /**
     * @param location
     */
    void postLocationResponse(Location location);

    /**
     * @param location
     */
    void getLocationResponse(Location location);

    /**
     * @param location
     */
    void putLocationResponse(Location location);

    /**
     * @param status
     */
    void deleteLocationResponse(boolean status);

    // Profiles

    /**
     * @param profileList
     */
    void getProfilesResponse(ProfileList profileList);

    /**
     * @param profile
     */
    void postProfileResponse(Profile profile);

    /**
     * @param profile
     */
    void getProfileResponse(Profile profile);

    /**
     * @param profile
     */
    void putProfileResponse(Profile profile);

    /**
     * @param status
     */
    void deleteProfileResponse(boolean status);

    // Notifications

    /**
     * @param notificationList
     */
    void getNotificationsResponse(NotificationList notificationList);

    /**
     * @param notification
     */
    void getNotificationResponse(Notification notification);

    /**
     * @param notification
     */
    void putNotificationResponse(Notification notification);

    // Device History

    /**
     * @param deviceHistoryList
     */
    void getDeviceHistoriesResponse(DeviceHistoryList deviceHistoryList);

    /**
     * @param deviceHistory
     */
    void getDeviceHistoryResponse(DeviceHistory deviceHistory);

    // ZWaveAPI Callbacks

    /**
     * @param zwaveDevice
     */
    void getZWaveDeviceResponse(ZWaveDevice zwaveDevice);

    // Other

    /**
     * This function is called when an internal error occurred.
     *
     * @param message
     * @param invalidateState message indicates a fault in the system or Z-Way server
     */
    void apiError(String message, boolean invalidateState);

    /**
     * This function is called when a server response status isn't 200.
     *
     * @param httpStatus
     * @param message
     * @param invalidateState message indicates a fault in the system or Z-Way server
     */
    void httpStatusError(int httpStatus, String message, boolean invalidateState);

    /**
     * This function is called when authentication error occurred.
     */
    void authenticationError();

    /**
     * This function is called when receiving data not in the correct format.
     *
     * @param message
     * @param invalidApiState message indicates a fault in the system or Z-Way server
     */
    void responseFormatError(String message, boolean invalidApiState);
}
