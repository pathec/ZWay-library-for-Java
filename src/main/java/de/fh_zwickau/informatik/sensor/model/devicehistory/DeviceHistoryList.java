/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor.model.devicehistory;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * The {@link DeviceHistoryList} represents a device history list. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class DeviceHistoryList {
    private List<DeviceHistory> mDeviceHistoryList;

    /**
     * Instantiate a device history list with default values.
     */
    public DeviceHistoryList() {
        mDeviceHistoryList = new ArrayList<DeviceHistory>();
    }

    public void addDeviceHistory(DeviceHistory deviceHistory) {
        mDeviceHistoryList.add(deviceHistory);
    }

    public List<DeviceHistory> getDeviceHistoryList() {
        return mDeviceHistoryList;
    }

    /**
     * Return the first occurrence of device history
     *
     * @param id ZAutomation device id
     * @return device history or null if not found
     */
    public DeviceHistory getDeviceHistoryByDeviceId(String deviceId) {
        for (DeviceHistory deviceHistory : mDeviceHistoryList) {
            if (deviceHistory.getDeviceId().equals(deviceId)) {
                return deviceHistory;
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this);
        for (DeviceHistory deviceHistory : mDeviceHistoryList) {
            toStringBuilder.append(deviceHistory);
        }

        return new ToStringBuilder(this).append("deviceHistoryList", toStringBuilder.toString()).toString();
    }
}
