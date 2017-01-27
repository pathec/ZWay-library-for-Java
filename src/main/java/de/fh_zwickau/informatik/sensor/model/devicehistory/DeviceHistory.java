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

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.gson.annotations.SerializedName;

/**
 * The {@link DeviceHistory} represents a history. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class DeviceHistory {
    @SerializedName("id")
    private String mDeviceId;
    @SerializedName("dT")
    private String mDeviceType;
    @SerializedName("mH")
    private ArrayList<DeviceHistoryData> mHistoryData;

    public DeviceHistory() {
        mDeviceId = "";
        mDeviceType = "";
        mHistoryData = new ArrayList<DeviceHistoryData>();
    }

    public String getDeviceId() {
        if (mDeviceId == null) {
            mDeviceId = "";
        }
        return mDeviceId;
    }

    public void setDeviceId(String deviceId) {
        this.mDeviceId = deviceId;
    }

    public String getDeviceType() {
        if (mDeviceType == null) {
            mDeviceType = "";
        }
        return mDeviceType;
    }

    public void setDeviceType(String deviceType) {
        this.mDeviceType = deviceType;
    }

    public ArrayList<DeviceHistoryData> getHistoryData() {
        if (mHistoryData == null) {
            mHistoryData = new ArrayList<DeviceHistoryData>();
        }
        return mHistoryData;
    }

    public void setHistoryData(ArrayList<DeviceHistoryData> historyData) {
        this.mHistoryData = historyData;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("deviceId", mDeviceId).append("deviceType", mDeviceType)
                .append("historyData", mHistoryData).toString();
    }
}
