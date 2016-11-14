/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor.model.devices;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.gson.annotations.SerializedName;

/**
 * The {@link Metrics} represents the metrics field of virtual device from ZAutomation API. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class Metrics {
    @SerializedName("icon")
    private String mIcon;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("level")
    private String mLevel;
    @SerializedName("probeTitle")
    private String mProbeTitle;
    @SerializedName("scaleTitle")
    private String mScaleTitle;

    @SerializedName("color")
    private Color mColor;

    @SerializedName("min")
    private Integer mMin;
    @SerializedName("max")
    private Integer mMax;

    /**
     * Instantiate a metrics object with default values.
     */
    public Metrics() {
        mIcon = "";
        mTitle = "";
        mLevel = "";
        mProbeTitle = "";
        mScaleTitle = "";
        mColor = new Color();
        mMin = 0;
        mMax = 0;
    }

    public String getIcon() {
        if (mIcon == null) {
            mIcon = "";
        }
        return mIcon;
    }

    public void setIcon(String icon) {
        this.mIcon = icon;
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

    public String getLevel() {
        if (mLevel == null) {
            mLevel = "";
        }
        return mLevel;
    }

    public void setLevel(String level) {
        this.mLevel = level;
    }

    public String getProbeTitle() {
        if (mProbeTitle == null) {
            mProbeTitle = "";
        }
        return mProbeTitle;
    }

    public void setProbeTitle(String probeTitle) {
        this.mProbeTitle = probeTitle;
    }

    public String getScaleTitle() {
        if (mScaleTitle == null) {
            mScaleTitle = "";
        }
        return mScaleTitle;
    }

    public void setScaleTitle(String scaleTitle) {
        this.mScaleTitle = scaleTitle;
    }

    public Color getColor() {
        if (mColor == null) {
            mColor = new Color();
        }
        return mColor;
    }

    public void setColor(Color color) {
        this.mColor = color;
    }

    public Integer getMin() {
        if (mMin == null) {
            mMin = 0;
        }
        return mMin;
    }

    public void setMin(Integer min) {
        this.mMin = min;
    }

    public Integer getMax() {
        if (mMax == null) {
            mMax = 0;
        }
        return mMax;
    }

    public void setMax(Integer max) {
        this.mMax = max;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("icon", mIcon).append("title", mTitle).append("level", mLevel)
                .append("probeTitle", mProbeTitle).append("scaleTitle", mScaleTitle).append("color", mColor)
                .append("min", mMin).append("max", mMax).toString();
    }
}
