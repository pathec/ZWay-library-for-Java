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
 * The {@link ZWaveDeviceInstanceCommandClass64DataMode} represents modes for command class 64 of Z-Wave device
 * instance. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class ZWaveDeviceInstanceCommandClass64DataMode {
    @SerializedName("modeName")
    private ZWaveDataElement mModeName;

    public ZWaveDeviceInstanceCommandClass64DataMode() {
        this.mModeName = new ZWaveDataElement();
    }

    public ZWaveDataElement getModeName() {
        return mModeName;
    }

    public void setModeName(ZWaveDataElement mModeName) {
        this.mModeName = mModeName;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("modeName", mModeName).toString();
    }
}
