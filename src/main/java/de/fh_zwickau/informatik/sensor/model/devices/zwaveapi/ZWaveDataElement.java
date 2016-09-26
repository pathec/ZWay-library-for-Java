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
 * The {@link ZWaveDataElement} represents a data element. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * Example:
 * "givenName": {
 * "invalidateTime": 1463302196,
 * "updateTime": 1466020714,
 * "type": "string",
 * "value": "iTemp Sensor"
 * }
 *
 * @author Patrick Hecker - Initial contribution
 */
public class ZWaveDataElement {
    @SerializedName("invalidateTime")
    private Integer mInvalidateTime;
    @SerializedName("updateTime")
    private Integer mUpdateTime;
    @SerializedName("type")
    private String mType;
    @SerializedName("value")
    private String mValue;

    /**
     * Instantiate a Z-Wave data element object with default values.
     */
    public ZWaveDataElement() {
        mInvalidateTime = -1;
        mUpdateTime = -1;
        mType = "";
        mValue = "";
    }

    public Integer getmInvalidateTime() {
        if (mInvalidateTime == null) {
            mInvalidateTime = -1;
        }
        return mInvalidateTime;
    }

    public void setInvalidateTime(Integer invalidateTime) {
        this.mInvalidateTime = invalidateTime;
    }

    public Integer getUpdateTime() {
        if (mUpdateTime == null) {
            mUpdateTime = -1;
        }
        return mUpdateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.mUpdateTime = updateTime;
    }

    public String getType() {
        if (mType == null) {
            mType = "";
        }
        return mType;
    }

    public void setType(String type) {
        this.mType = type;
    }

    public String getValue() {
        if (mValue == null) {
            mValue = "";
        }
        return mValue;
    }

    public void setValue(String value) {
        this.mValue = value;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("invalidateTime", mInvalidateTime).append("updateTime", mUpdateTime)
                .append("type", mType).append("value", mValue).toString();
    }

}
