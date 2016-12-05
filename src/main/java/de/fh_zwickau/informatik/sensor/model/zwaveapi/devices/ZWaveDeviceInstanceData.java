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

import de.fh_zwickau.informatik.sensor.model.zwaveapi.ZWaveDataElement;

/**
 * The {@link ZWaveDeviceInstanceData} represents the data field of Z-Wave device instance. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class ZWaveDeviceInstanceData {
    @SerializedName("dynamic")
    private ZWaveDataElement mDynamic;
    @SerializedName("genericType")
    private ZWaveDataElement mGenericType;
    @SerializedName("specificType")
    private ZWaveDataElement mSpecificType;
    @SerializedName("nodeInfoFrame")
    private ZWaveDataElement mNodeInfoFrame;

    public ZWaveDeviceInstanceData() {
        this.mDynamic = new ZWaveDataElement();
        this.mGenericType = new ZWaveDataElement();
        this.mSpecificType = new ZWaveDataElement();
        this.mNodeInfoFrame = new ZWaveDataElement();
    }

    public ZWaveDataElement getDynamic() {
        if (mDynamic == null) {
            mDynamic = new ZWaveDataElement();
        }
        return mDynamic;
    }

    public void setDynamic(ZWaveDataElement dynamic) {
        this.mDynamic = dynamic;
    }

    public ZWaveDataElement getGenericType() {
        if (mGenericType == null) {
            mGenericType = new ZWaveDataElement();
        }
        return mGenericType;
    }

    public void setGenericType(ZWaveDataElement genericType) {
        this.mGenericType = genericType;
    }

    public ZWaveDataElement getSpecificType() {
        if (mSpecificType == null) {
            mSpecificType = new ZWaveDataElement();
        }
        return mSpecificType;
    }

    public void setSpecificType(ZWaveDataElement specificType) {
        this.mSpecificType = specificType;
    }

    public ZWaveDataElement getNodeInfoFrame() {
        if (mNodeInfoFrame == null) {
            mNodeInfoFrame = new ZWaveDataElement();
        }
        return mNodeInfoFrame;
    }

    public void setNodeInfoFrame(ZWaveDataElement nodeInfoFrame) {
        this.mNodeInfoFrame = nodeInfoFrame;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("dynamic", mDynamic).append("genericType", mGenericType)
                .append("specificType", mSpecificType).append("nodeInfoFrame", mNodeInfoFrame).toString();
    }
}
