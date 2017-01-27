/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor.model.devicehistory;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

/**
 * The {@link DeviceHistoryListDeserializer} build a device history list based on JSON data. See
 * also: http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class DeviceHistoryListDeserializer {
    /**
     * @param deviceHistoriesAsJson device history list as JSON data
     * @return device history list
     */
    public DeviceHistoryList deserializeDeviceHistoryList(JsonArray deviceHistoriesAsJson) {
        DeviceHistoryList deviceHistoryList = new DeviceHistoryList();

        for (JsonElement deviceHistoryAsJson : deviceHistoriesAsJson) {
            DeviceHistory deviceHistory = deserializeDeviceHistory(deviceHistoryAsJson);

            if (deviceHistory != null) {
                deviceHistoryList.addDeviceHistory(deviceHistory);
            }
        }

        return deviceHistoryList;
    }

    /**
     * @param deviceHistoryAsJson a single device history as JSON data
     * @return deviceHistory
     */
    public DeviceHistory deserializeDeviceHistory(JsonElement deviceHistoryAsJson) {
        Gson gson = new Gson();

        DeviceHistory deviceHistory = gson.fromJson(deviceHistoryAsJson, DeviceHistory.class);

        return deviceHistory;
    }
}
