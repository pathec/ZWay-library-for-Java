/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor.model.devices.zwaveapi;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.gson.annotations.SerializedName;

/**
 * The {@link ZWaveDevice} represents a device from ZWave API. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class ZWaveDevice {
    // TODO ZWaveDeviceInstances
    @SerializedName("data")
    private ZWaveDeviceData mData;
    @SerializedName("id")
    private Integer mId;

    /**
     * Instantiate a Z-Wave device object with default values.
     */
    public ZWaveDevice() {
        mData = new ZWaveDeviceData();
        mId = -1;
    }

    public ZWaveDeviceData getData() {
        if (mData == null) {
            mData = new ZWaveDeviceData();
        }
        return mData;
    }

    public void setData(ZWaveDeviceData data) {
        this.mData = data;
    }

    public Integer getId() {
        if (mId == null) {
            mId = -1;
        }
        return mId;
    }

    public void setId(Integer id) {
        this.mId = id;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("data", mData).append("id", mId).toString();
    }
}
