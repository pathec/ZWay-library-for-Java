/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor.model.notifications;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.gson.annotations.SerializedName;

/**
 * The {@link Notification} represents a notification. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class Notification {
    @SerializedName("id")
    private Integer mId;
    @SerializedName("timestamp")
    private Date mTimestamp;
    @SerializedName("level")
    private String mLevel;
    @SerializedName("message")
    private Message mMessage;
    @SerializedName("type")
    private String mType;
    @SerializedName("source")
    private String mSource;

    /**
     * Instantiate a notification with default values.
     */
    public Notification() {
        mId = -1;
        mTimestamp = new Date();
        mLevel = "";
        mMessage = new Message();
        mType = "";
        mSource = "";
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

    public Date getTimestamp() {
        if (mTimestamp == null) {
            mTimestamp = new Date();
        }
        return mTimestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.mTimestamp = timestamp;
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

    public Message getMessage() {
        if (mMessage == null) {
            mMessage = new Message();
        }
        return mMessage;
    }

    public void setMessage(Message message) {
        this.mMessage = message;
    }

    public String getType() {
        if (mType == null) {
            mType = "";
        }
        return mType;
    }

    public void setType(String type) {
        this.mType = type;
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

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", mId).append("timestamp", mTimestamp).append("level", mLevel)
                .append("message", mMessage).append("type", mType).append("source", mSource).toString();
    }
}
