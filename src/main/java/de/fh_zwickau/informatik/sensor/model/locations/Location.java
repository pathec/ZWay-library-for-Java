/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor.model.locations;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.gson.annotations.SerializedName;

/**
 * The {@link Location} represents a location. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class Location {
    @SerializedName("id")
    private Integer mId;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("user_img")
    private String mUserImg;

    /**
     * Instantiate a location with default values.
     */
    public Location() {
        mId = -1;
        mTitle = "";
        mUserImg = "";
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

    public String getTitle() {
        if (mTitle == null) {
            mTitle = "";
        }
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getUserImg() {
        if (mUserImg == null) {
            mUserImg = "";
        }
        return mUserImg;
    }

    public void setUserImg(String userImg) {
        this.mUserImg = userImg;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", mId).append("title", mTitle).append("userImg", mUserImg)
                .toString();
    }
}
