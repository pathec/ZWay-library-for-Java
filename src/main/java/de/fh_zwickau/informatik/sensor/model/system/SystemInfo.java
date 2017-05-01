/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor.model.system;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.gson.annotations.SerializedName;

/**
 * The {@link SystemInfo} represents a data element. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * Example:
 * "data": {
 * "first_start_up": "2016-08-03T19:24:13.208z",
 * "count_of_reconnects": 52,
 * "current_firmware": "2.3.4",
 * "current_firmware_majurity": "..."
 * }
 *
 * @author Patrick Hecker - Initial contribution
 */
public class SystemInfo {
    @SerializedName("first_start_up")
    private Date mFirstStartUp;
    @SerializedName("count_of_reconnects")
    private Integer mCountOfReconnects;
    @SerializedName("current_firmware")
    private String mCurrentFirmware;
    @SerializedName("current_firmware_majurity")
    private String mCurrentFirmwareMajurity;

    /**
     * Instantiate a system info element object with default values.
     */

    public SystemInfo() {
        mFirstStartUp = new Date();
        mCountOfReconnects = -1;
        mCurrentFirmware = "";
        mCurrentFirmwareMajurity = "";
    }

    public Date getFirstStartUp() {
        if (mFirstStartUp == null) {
            mFirstStartUp = new Date();
        }
        return mFirstStartUp;
    }

    public void setFirstStartUp(Date firstStartUp) {
        this.mFirstStartUp = firstStartUp;
    }

    public Integer getCountOfReconnects() {
        if (mCountOfReconnects == null) {
            mCountOfReconnects = -1;
        }
        return mCountOfReconnects;
    }

    public void setCountOfReconnects(Integer countOfReconnects) {
        this.mCountOfReconnects = countOfReconnects;
    }

    public String getCurrentFirmware() {
        if (mCurrentFirmware == null) {
            mCurrentFirmware = "";
        }
        return mCurrentFirmware;
    }

    public void setCurrentFirmware(String currentFirmware) {
        this.mCurrentFirmware = currentFirmware;
    }

    public String getCurrentFirmwareMajurity() {
        if (mCurrentFirmwareMajurity == null) {
            mCurrentFirmwareMajurity = "";
        }
        return mCurrentFirmwareMajurity;
    }

    public void setCurrentFirmwareMajurity(String currentFirmwareMajurity) {
        this.mCurrentFirmwareMajurity = currentFirmwareMajurity;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("first_start_up", mFirstStartUp)
                .append("count_of_reconnects", mCountOfReconnects).append("current_firmware", mCurrentFirmware)
                .append("current_firmware_majurity", mCurrentFirmwareMajurity).toString();
    }
}
