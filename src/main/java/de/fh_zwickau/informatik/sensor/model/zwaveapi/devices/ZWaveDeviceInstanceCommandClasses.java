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
 * The {@link ZWaveDeviceInstanceCommandClasses} represents the data field of Z-Wave device instance. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class ZWaveDeviceInstanceCommandClasses {
    @SerializedName("64")
    private ZWaveDeviceInstanceCommandClass64 m64;

    public ZWaveDeviceInstanceCommandClasses() {
        this.m64 = new ZWaveDeviceInstanceCommandClass64();
    }

    public ZWaveDeviceInstanceCommandClass64 get64() {
        if (m64 == null) {
            m64 = new ZWaveDeviceInstanceCommandClass64();
        }
        return m64;
    }

    public void set64(ZWaveDeviceInstanceCommandClass64 p64) {
        this.m64 = p64;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("64", m64).toString();
    }
}
