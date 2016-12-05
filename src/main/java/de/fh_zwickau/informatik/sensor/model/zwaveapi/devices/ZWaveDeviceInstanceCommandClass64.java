/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor.model.zwaveapi.devices;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.gson.annotations.SerializedName;

/**
 * The {@link ZWaveDeviceInstanceCommandClass64} represents command class of Z-Wave device instance. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class ZWaveDeviceInstanceCommandClass64 {
    @SerializedName("name")
    private String mName;
    @SerializedName("id")
    private Integer mId;
    @SerializedName("data")
    private ZWaveDeviceInstanceCommandClass64Data mData;

    public ZWaveDeviceInstanceCommandClass64() {
        this.mName = "";
        this.mId = -1;
        this.mData = new ZWaveDeviceInstanceCommandClass64Data();
    }

    public ZWaveDeviceInstanceCommandClass64Data getData() {
        if (mData == null) {
            mData = new ZWaveDeviceInstanceCommandClass64Data();
        }
        return mData;
    }

    public void setData(ZWaveDeviceInstanceCommandClass64Data data) {
        this.mData = data;
    }

    public String getName() {
        if (mName == null) {
            mName = "";
        }
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
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
     * extra methods (not zway)
     */
    public Map<Integer, String> getThermostatModes() {
        Map<Integer, String> modes = new HashMap<Integer, String>();

        if (mData.get0() != null && !mData.get0().getModeName().getValue().equals("")) {
            modes.put(0, mData.get0().getModeName().getValue());
        }

        if (mData.get1() != null && !mData.get1().getModeName().getValue().equals("")) {
            modes.put(1, mData.get1().getModeName().getValue());
        }

        if (mData.get2() != null && !mData.get2().getModeName().getValue().equals("")) {
            modes.put(2, mData.get2().getModeName().getValue());
        }

        if (mData.get11() != null && !mData.get11().getModeName().getValue().equals("")) {
            modes.put(11, mData.get11().getModeName().getValue());
        }

        if (mData.get31() != null && !mData.get31().getModeName().getValue().equals("")) {
            modes.put(31, mData.get31().getModeName().getValue());
        }

        return modes;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("name", mName).append("id", mId).append("data", mData).toString();
    }
}
