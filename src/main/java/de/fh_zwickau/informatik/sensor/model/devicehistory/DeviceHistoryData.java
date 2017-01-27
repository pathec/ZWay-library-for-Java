/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor.model.devicehistory;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.gson.annotations.SerializedName;

/**
 * The {@link DeviceHistoryData} represents one history data. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class DeviceHistoryData {
    @SerializedName("id")
    private Integer mId;
    @SerializedName("l")
    private String mLevel;

    public DeviceHistoryData() {
        mId = -1;
        mLevel = "";
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

    public String getLevel() {
        if (mLevel == null) {
            mLevel = "";
        }
        return mLevel;
    }

    public void setLevel(String level) {
        this.mLevel = level;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", mId).append("level", mLevel).toString();
    }
}
