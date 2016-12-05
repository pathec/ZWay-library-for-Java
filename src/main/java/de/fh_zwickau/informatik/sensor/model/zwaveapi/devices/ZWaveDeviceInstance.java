/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor.model.zwaveapi.devices;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.gson.annotations.SerializedName;

/**
 * The {@link ZWaveDeviceInstance} represents a device instance from ZWave API. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class ZWaveDeviceInstance {
    @SerializedName("commandClasses")
    private ZWaveDeviceInstanceCommandClasses mCommandClasses;
    @SerializedName("data")
    private ZWaveDeviceInstanceData mData;
    @SerializedName("id")
    private Integer mId;

    public ZWaveDeviceInstance() {
        mCommandClasses = new ZWaveDeviceInstanceCommandClasses();
        mData = new ZWaveDeviceInstanceData();
        mId = -1;
    }

    public ZWaveDeviceInstanceCommandClasses getCommandClasses() {
        if (mCommandClasses == null) {
            mCommandClasses = new ZWaveDeviceInstanceCommandClasses();
        }
        return mCommandClasses;
    }

    public void setCommandClasses(ZWaveDeviceInstanceCommandClasses commandClasses) {
        this.mCommandClasses = commandClasses;
    }

    public ZWaveDeviceInstanceData getData() {
        if (mData == null) {
            mData = new ZWaveDeviceInstanceData();
        }
        return mData;
    }

    public void setData(ZWaveDeviceInstanceData data) {
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
        return new ToStringBuilder(this).append("id", mId).append("commandClasses", mCommandClasses)
                .append("data", mData).toString();
    }
}
