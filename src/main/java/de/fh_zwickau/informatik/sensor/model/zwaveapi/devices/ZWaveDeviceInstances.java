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
 * The {@link ZWaveDeviceInstances} represents a list of device instances from ZWave API. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class ZWaveDeviceInstances {
    @SerializedName("0")
    private ZWaveDeviceInstance m0;

    public ZWaveDeviceInstances() {
        m0 = new ZWaveDeviceInstance();
    }

    public ZWaveDeviceInstance get0() {
        if (m0 == null) {
            m0 = new ZWaveDeviceInstance();
        }
        return m0;
    }

    public void set0(ZWaveDeviceInstance p0) {
        this.m0 = p0;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("0", m0).toString();
    }
}
