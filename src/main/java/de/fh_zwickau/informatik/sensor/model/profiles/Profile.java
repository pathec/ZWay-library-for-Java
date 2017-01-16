/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor.model.profiles;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.gson.annotations.SerializedName;

/**
 * The {@link Profile} represents a notification. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class Profile {
    @SerializedName("id")
    private Integer mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("lang")
    private String mLang;
    @SerializedName("color")
    private String mColor;
    @SerializedName("dashboard")
    private Set<String> mDashboard;
    @SerializedName("interval")
    private Integer mInterval;
    @SerializedName("rooms")
    private Set<Integer> mRooms;
    @SerializedName("expert_view")
    private Boolean mExpertView;
    @SerializedName("hide_all_device_events")
    private Boolean mHideAllDeviceEvents;
    @SerializedName("hide_system_events")
    private Boolean mHideSystemEvents;
    @SerializedName("hide_single_device_events")
    private Set<String> mHideSingleDeviceEvents;
    @SerializedName("email")
    private String mEmail;

    /**
     * Instantiate a profile with default values.
     */
    public Profile() {
        mId = -1;
        mName = "";
        mLang = "";
        mColor = "";
        mDashboard = new HashSet<>();
        mInterval = -1;
        mRooms = new HashSet<>();
        mExpertView = false;
        mHideAllDeviceEvents = false;
        mHideSystemEvents = false;
        mHideSingleDeviceEvents = new HashSet<>();
        mEmail = "";
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

    public String getName() {
        if (mName == null) {
            mName = "";
        }
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getLang() {
        if (mLang == null) {
            mLang = "";
        }
        return mLang;
    }

    public void setLang(String lang) {
        this.mLang = lang;
    }

    public String getColor() {
        if (mColor == null) {
            mColor = "";
        }
        return mColor;
    }

    public void setColor(String color) {
        this.mColor = color;
    }

    public Set<String> getDashboard() {
        if (mDashboard == null) {
            mDashboard = new HashSet<>();
        }
        return mDashboard;
    }

    public void setDashboard(Set<String> dashboard) {
        this.mDashboard = dashboard;
    }

    public Integer getInterval() {
        if (mInterval == null) {
            mInterval = -1;
        }
        return mInterval;
    }

    public void setInterval(Integer interval) {
        this.mInterval = interval;
    }

    public Set<Integer> getRooms() {
        if (mRooms == null) {
            mRooms = new HashSet<>();
        }
        return mRooms;
    }

    public void setRooms(Set<Integer> rooms) {
        this.mRooms = rooms;
    }

    public Boolean getExpertView() {
        if (mExpertView == null) {
            mExpertView = false;
        }
        return mExpertView;
    }

    public void setExpertView(Boolean expertView) {
        this.mExpertView = expertView;
    }

    public Boolean getHideAllDeviceEvents() {
        if (mHideAllDeviceEvents == null) {
            mHideAllDeviceEvents = false;
        }
        return mHideAllDeviceEvents;
    }

    public void setHideAllDeviceEvents(Boolean hideAllDeviceEvents) {
        this.mHideAllDeviceEvents = hideAllDeviceEvents;
    }

    public Boolean getHideSystemEvents() {
        if (mHideSystemEvents == null) {
            mHideSystemEvents = false;
        }
        return mHideSystemEvents;
    }

    public void setHideSystemEvents(Boolean hideSystemEvents) {
        this.mHideSystemEvents = hideSystemEvents;
    }

    public Set<String> getHideSingleDeviceEvents() {
        if (mHideSingleDeviceEvents == null) {
            mHideSingleDeviceEvents = new HashSet<>();
        }
        return mHideSingleDeviceEvents;
    }

    public void setHideSingleDeviceEvents(Set<String> hideSingleDeviceEvents) {
        this.mHideSingleDeviceEvents = hideSingleDeviceEvents;
    }

    public String getEmail() {
        if (mEmail == null) {
            mEmail = "";
        }
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", mId).append("name", mName).append("lang", mLang)
                .append("color", mColor).append("dashboard", mDashboard).append("interval", mInterval)
                .append("rooms", mRooms).append("expertView", mExpertView)
                .append("hideAllDeviceEvents", mHideAllDeviceEvents).append("hideSystemEvents", mHideSystemEvents)
                .append("hideSingleDeviceEvents", mHideSingleDeviceEvents).append("email", mEmail).toString();
    }
}
