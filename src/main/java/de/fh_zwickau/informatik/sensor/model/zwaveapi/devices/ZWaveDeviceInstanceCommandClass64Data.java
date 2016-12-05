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
 * The {@link ZWaveDeviceInstanceCommandClass64Data} represents the data field of command class of Z-Wave device
 * instance.
 * See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class ZWaveDeviceInstanceCommandClass64Data {
    @SerializedName("support")
    private ZWaveDataElement mSupported;
    @SerializedName("version")
    private ZWaveDataElement mVersion;
    @SerializedName("security")
    private ZWaveDataElement mSecurity;
    @SerializedName("interviewDone")
    private ZWaveDataElement mInterviewDone;
    @SerializedName("interviewCounter")
    private ZWaveDataElement mInterviewCounter;
    @SerializedName("modemask")
    private ZWaveDataElement mModemask;
    @SerializedName("mode")
    private ZWaveDataElement mMode;
    @SerializedName("0")
    private ZWaveDeviceInstanceCommandClass64DataMode m0;
    @SerializedName("1")
    private ZWaveDeviceInstanceCommandClass64DataMode m1;
    @SerializedName("2")
    private ZWaveDeviceInstanceCommandClass64DataMode m2;
    @SerializedName("11")
    private ZWaveDeviceInstanceCommandClass64DataMode m11;
    @SerializedName("31")
    private ZWaveDeviceInstanceCommandClass64DataMode m31;

    public ZWaveDeviceInstanceCommandClass64Data() {
        mSupported = new ZWaveDataElement();
        mVersion = new ZWaveDataElement();
        mSecurity = new ZWaveDataElement();
        mInterviewDone = new ZWaveDataElement();
        mInterviewCounter = new ZWaveDataElement();
        mModemask = new ZWaveDataElement();
        mMode = new ZWaveDataElement();
        m0 = new ZWaveDeviceInstanceCommandClass64DataMode();
        m1 = new ZWaveDeviceInstanceCommandClass64DataMode();
        m2 = new ZWaveDeviceInstanceCommandClass64DataMode();
        m11 = new ZWaveDeviceInstanceCommandClass64DataMode();
        m31 = new ZWaveDeviceInstanceCommandClass64DataMode();
    }

    public ZWaveDataElement getSupported() {
        if (mSupported == null) {
            mSupported = new ZWaveDataElement();
        }
        return mSupported;
    }

    public void setSupported(ZWaveDataElement supported) {
        this.mSupported = supported;
    }

    public ZWaveDataElement getVersion() {
        if (mVersion == null) {
            mVersion = new ZWaveDataElement();
        }
        return mVersion;
    }

    public void setVersion(ZWaveDataElement version) {
        this.mVersion = version;
    }

    public ZWaveDataElement getSecurity() {
        if (mSecurity == null) {
            mSecurity = new ZWaveDataElement();
        }
        return mSecurity;
    }

    public void setSecurity(ZWaveDataElement security) {
        this.mSecurity = security;
    }

    public ZWaveDataElement getInterviewDone() {
        if (mInterviewDone == null) {
            mInterviewDone = new ZWaveDataElement();
        }
        return mInterviewDone;
    }

    public void setInterviewDone(ZWaveDataElement interviewDone) {
        this.mInterviewDone = interviewDone;
    }

    public ZWaveDataElement getInterviewCounter() {
        if (mInterviewCounter == null) {
            mInterviewCounter = new ZWaveDataElement();
        }
        return mInterviewCounter;
    }

    public void setInterviewCounter(ZWaveDataElement interviewCounter) {
        this.mInterviewCounter = interviewCounter;
    }

    public ZWaveDataElement getModemask() {
        if (mModemask == null) {
            mModemask = new ZWaveDataElement();
        }
        return mModemask;
    }

    public void setModemask(ZWaveDataElement modemask) {
        this.mModemask = modemask;
    }

    public ZWaveDataElement getMode() {
        if (mMode == null) {
            mMode = new ZWaveDataElement();
        }
        return mMode;
    }

    public void setMode(ZWaveDataElement mode) {
        this.mMode = mode;
    }

    public ZWaveDeviceInstanceCommandClass64DataMode get0() {
        if (m0 == null) {
            m0 = new ZWaveDeviceInstanceCommandClass64DataMode();
        }
        return m0;
    }

    public void set0(ZWaveDeviceInstanceCommandClass64DataMode p0) {
        this.m0 = p0;
    }

    public ZWaveDeviceInstanceCommandClass64DataMode get1() {
        if (m1 == null) {
            m1 = new ZWaveDeviceInstanceCommandClass64DataMode();
        }
        return m1;
    }

    public void set1(ZWaveDeviceInstanceCommandClass64DataMode p1) {
        this.m1 = p1;
    }

    public ZWaveDeviceInstanceCommandClass64DataMode get2() {
        if (m2 == null) {
            m2 = new ZWaveDeviceInstanceCommandClass64DataMode();
        }
        return m2;
    }

    public void set2(ZWaveDeviceInstanceCommandClass64DataMode p2) {
        this.m2 = p2;
    }

    public ZWaveDeviceInstanceCommandClass64DataMode get11() {
        if (m11 == null) {
            m11 = new ZWaveDeviceInstanceCommandClass64DataMode();
        }
        return m11;
    }

    public void set11(ZWaveDeviceInstanceCommandClass64DataMode p11) {
        this.m11 = p11;
    }

    public ZWaveDeviceInstanceCommandClass64DataMode get31() {
        if (m31 == null) {
            m31 = new ZWaveDeviceInstanceCommandClass64DataMode();
        }
        return m31;
    }

    public void set31(ZWaveDeviceInstanceCommandClass64DataMode p31) {
        this.m31 = p31;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("supported", mSupported).append("version", mVersion)
                .append("security", mSecurity).append("interviewDone", mInterviewDone)
                .append("interviewCounter", mInterviewCounter).append("modemask", mModemask).append("mode", mMode)
                .append("0", m0).append("1", m1).append("11", m11).append("31", m31).toString();
    }
}
