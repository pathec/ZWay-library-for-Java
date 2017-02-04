/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor.model.instances;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.gson.annotations.SerializedName;

/**
 * The {@link Instance} represents a instance. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public abstract class Instance {
    @SerializedName("instanceId")
    private String mInstanceId;
    @SerializedName("moduleId")
    private String mModuleId;
    @SerializedName("active")
    private Boolean mActive;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("id")
    private Integer mId;
    @SerializedName("creationTime")
    private Long mCreationTime;

    /**
     * Instantiate a instance with default values.
     */
    public Instance() {
        mInstanceId = "";
        mModuleId = "";
        mActive = true;
        mTitle = "";
        mId = -1;
        mCreationTime = new Date().getTime() / 1000;
    }

    public String getInstanceId() {
        if (mInstanceId == null) {
            mInstanceId = "";
        }
        return mInstanceId;
    }

    public void setInstanceId(String instanceId) {
        this.mInstanceId = instanceId;
    }

    public String getModuleId() {
        if (mModuleId == null) {
            mModuleId = "";
        }
        return mModuleId;
    }

    public void setModuleId(String moduleId) {
        this.mModuleId = moduleId;
    }

    public Boolean getActive() {
        if (mActive == null) {
            mActive = true;
        }
        return mActive;
    }

    public void setActive(Boolean active) {
        this.mActive = active;
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

    public Integer getId() {
        if (mId == null) {
            mId = -1;
        }
        return mId;
    }

    public void setId(Integer id) {
        this.mId = id;
    }

    public Long getCreationTime() {
        if (mCreationTime == null) {
            mCreationTime = -1l;
        }
        return mCreationTime;
    }

    public void setCreationTime(Long creationTime) {
        this.mCreationTime = creationTime;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("instanceId", mInstanceId).append("moduleId", mModuleId)
                .append("active", mActive).append("title", mTitle).append("id", mId)
                .append("creationTime", mCreationTime).toString();
    }
}
