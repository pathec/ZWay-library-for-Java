/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor.model.notifications;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.gson.annotations.SerializedName;

/**
 * The {@link Message} represents the message field of a notification. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class Message {
    @SerializedName("dev")
    private String mDev;
    @SerializedName("l")
    private String mL;

    public Message() {
        mDev = "";
        mL = "";
    }

    public String getDev() {
        if (mDev == null) {
            mDev = "";
        }
        return mDev;
    }

    public void setDev(String dev) {
        this.mDev = dev;
    }

    public String getL() {
        if (mL == null) {
            mL = "";
        }
        return mL;
    }

    public void setL(String l) {
        this.mL = l;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("dev", mDev).append("l", mL).toString();
    }
}
