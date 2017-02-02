/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor.model.icons;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.gson.annotations.SerializedName;

/**
 * The {@link Icon} represents a icon. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class Icon {
    @SerializedName("file")
    private String mFile;
    @SerializedName("orgfile")
    private String mOrgFile;
    @SerializedName("source")
    private String mSource;
    @SerializedName("name")
    private String mName;
    @SerializedName("id")
    private String mId;
    @SerializedName("timestamp")
    private Long mTimestamp;
    @SerializedName("source_title")
    private String mSourceTitle;

    /**
     * Instantiate a icon with default values.
     */
    public Icon() {
        mFile = "";
        mOrgFile = "";
        mSource = "";
        mName = "";
        mId = "";
        mTimestamp = -1l;
        mSourceTitle = "";
    }

    public String getFile() {
        if (mFile == null) {
            mFile = "";
        }
        return mFile;
    }

    public void setFile(String file) {
        this.mFile = file;
    }

    public String getOrgFile() {
        if (mOrgFile == null) {
            mOrgFile = "";
        }
        return mOrgFile;
    }

    public void setOrgFile(String orgFile) {
        this.mOrgFile = orgFile;
    }

    public String getSource() {
        if (mSource == null) {
            mSource = "";
        }
        return mSource;
    }

    public void setSource(String source) {
        this.mSource = source;
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

    public String getId() {
        if (mId == null) {
            mId = "";
        }
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public Long getTimestamp() {
        if (mTimestamp == null) {
            mTimestamp = -1l;
        }
        return mTimestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.mTimestamp = timestamp;
    }

    public String getSourceTitle() {
        if (mSourceTitle == null) {
            mSourceTitle = "";
        }
        return mSourceTitle;
    }

    public void setSourceTitle(String sourceTitle) {
        this.mSourceTitle = sourceTitle;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("file", mFile).append("orgfile", mOrgFile).append("source", mSource)
                .append("name", mName).append("id", mId).append("timestamp", mTimestamp)
                .append("sourceTitle", mSourceTitle).toString();
    }
}
