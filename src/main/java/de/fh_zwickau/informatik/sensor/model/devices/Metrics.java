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

    @SerializedName("url")
    private String mCameraStreamUrl;
    @SerializedName("hasZoomIn")
    private Boolean mCameraHasZoomIn;
    @SerializedName("hasZoomOut")
    private Boolean mCameraHasZoomOut;
    @SerializedName("hasLeft")
    private Boolean mCameraHasLeft;
    @SerializedName("hasRight")
    private Boolean mCameraHasRight;
    @SerializedName("hasUp")
    private Boolean mCameraHasUp;
    @SerializedName("hasDown")
    private Boolean mCameraHasDown;
    @SerializedName("hasOpen")
    private Boolean mCameraHasOpen;
    @SerializedName("hasClose")
    private Boolean mCameraHasClose;

    @SerializedName("text")
    private String mText;

    @SerializedName("currentScene")
    private Integer mDiscreteCurrentScene;
    @SerializedName("keyAttribute")
    private Integer mDiscreteKeyAttribute;
    @SerializedName("state")
    private String mDiscreteState;
    @SerializedName("maxScenes")
    private Integer mDiscreteMaxScenes;
    @SerializedName("cnt")
    private Integer mDiscreteCount;
    @SerializedName("type")
    private String mDiscreteType;

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

        mCameraStreamUrl = "";
        mCameraHasZoomIn = false;
        mCameraHasZoomOut = false;
        mCameraHasLeft = false;
        mCameraHasRight = false;
        mCameraHasUp = false;
        mCameraHasDown = false;
        mCameraHasOpen = false;
        mCameraHasClose = false;

        mText = "";

        mDiscreteCurrentScene = 0;
        mDiscreteKeyAttribute = 0;
        mDiscreteState = "";
        mDiscreteMaxScenes = 0;
        mDiscreteCount = 0;
        mDiscreteType = "";
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
     * Camera
     */

    public String getCameraStreamUrl() {
        if (mCameraStreamUrl == null) {
            mCameraStreamUrl = "";
        }
        return mCameraStreamUrl;
    }

    public void setCameraStreamUrl(String cameraStreamUrl) {
        this.mCameraStreamUrl = cameraStreamUrl;
    }

    public Boolean getCameraHasZoomIn() {
        if (mCameraHasZoomIn == null) {
            mCameraHasZoomIn = false;
        }
        return mCameraHasZoomIn;
    }

    public void setCameraHasZoomIn(Boolean cameraHasZoomIn) {
        this.mCameraHasZoomIn = cameraHasZoomIn;
    }

    public Boolean getCameraHasZoomOut() {
        if (mCameraHasZoomOut == null) {
            mCameraHasZoomOut = false;
        }
        return mCameraHasZoomOut;
    }

    public void setCameraHasZoomOut(Boolean cameraHasZoomOut) {
        this.mCameraHasZoomOut = cameraHasZoomOut;
    }

    public Boolean getCameraHasLeft() {
        if (mCameraHasLeft == null) {
            mCameraHasLeft = false;
        }
        return mCameraHasLeft;
    }

    public void setCameraHasLeft(Boolean cameraHasLeft) {
        this.mCameraHasLeft = cameraHasLeft;
    }

    public Boolean getCameraHasRight() {
        if (mCameraHasRight == null) {
            mCameraHasRight = false;
        }
        return mCameraHasRight;
    }

    public void setCameraHasRight(Boolean cameraHasRight) {
        this.mCameraHasRight = cameraHasRight;
    }

    public Boolean getCameraHasUp() {
        if (mCameraHasUp == null) {
            mCameraHasUp = false;
        }
        return mCameraHasUp;
    }

    public void setCameraHasUp(Boolean cameraHasUp) {
        this.mCameraHasUp = cameraHasUp;
    }

    public Boolean getCameraHasDown() {
        if (mCameraHasDown == null) {
            mCameraHasDown = false;
        }
        return mCameraHasDown;
    }

    public void setCameraHasDown(Boolean cameraHasDown) {
        this.mCameraHasDown = cameraHasDown;
    }

    public Boolean getCameraHasOpen() {
        if (mCameraHasOpen == null) {
            mCameraHasOpen = false;
        }
        return mCameraHasOpen;
    }

    public void setCameraHasOpen(Boolean cameraHasOpen) {
        this.mCameraHasOpen = cameraHasOpen;
    }

    public Boolean getCameraHasClose() {
        if (mCameraHasClose == null) {
            mCameraHasClose = false;
        }
        return mCameraHasClose;
    }

    public void setCameraHasClose(Boolean cameraHasClose) {
        this.mCameraHasClose = cameraHasClose;
    }

    public String getText() {
        if (mText == null) {
            mText = "";
        }
        return mText;
    }

    public void setText(String text) {
        this.mText = text;
    }

    public Integer getDiscreteCurrentScene() {
        if (mDiscreteCurrentScene == null) {
            mDiscreteCurrentScene = 0;
        }
        return mDiscreteCurrentScene;
    }

    public void setDiscreteCurrentScene(Integer discreteCurrentScene) {
        this.mDiscreteCurrentScene = discreteCurrentScene;
    }

    public Integer getDiscreteKeyAttribute() {
        if (mDiscreteKeyAttribute == null) {
            mDiscreteKeyAttribute = 0;
        }
        return mDiscreteKeyAttribute;
    }

    public void setDiscreteKeyAttribute(Integer discreteKeyAttribute) {
        this.mDiscreteKeyAttribute = discreteKeyAttribute;
    }

    public String getDiscreteState() {
        if (mDiscreteState == null) {
            mDiscreteState = "";
        }
        return mDiscreteState;
    }

    public void setDiscreteState(String discreteState) {
        this.mDiscreteState = discreteState;
    }

    public Integer getDiscreteMaxScenes() {
        if (mDiscreteMaxScenes == null) {
            mDiscreteMaxScenes = 0;
        }
        return mDiscreteMaxScenes;
    }

    public void setDiscreteMaxScenes(Integer discreteMaxScenes) {
        this.mDiscreteMaxScenes = discreteMaxScenes;
    }

    public Integer getDiscreteCount() {
        if (mDiscreteCount == null) {
            mDiscreteCount = 0;
        }
        return mDiscreteCount;
    }

    public void setDiscreteCount(Integer discreteCount) {
        this.mDiscreteCount = discreteCount;
    }

    public String getDiscreteType() {
        if (mDiscreteType == null) {
            mDiscreteType = "";
        }
        return mDiscreteType;
    }

    public void setDiscreteType(String discreteType) {
        this.mDiscreteType = discreteType;
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
                .append("min", mMin).append("max", mMax).append("url", mCameraStreamUrl)
                .append("hasZoomIn", mCameraHasZoomIn).append("hasZoomOut", mCameraHasZoomOut)
                .append("hasLeft", mCameraHasLeft).append("hasRight", mCameraHasRight).append("hasUp", mCameraHasUp)
                .append("hasDown", mCameraHasDown).append("hasOpen", mCameraHasOpen).append("hasClose", mCameraHasClose)
                .append("text", mText).append("currentScene", mDiscreteCurrentScene)
                .append("keyAttribute", mDiscreteKeyAttribute).append("state", mDiscreteState)
                .append("maxScenes", mDiscreteMaxScenes).append("cnt", mDiscreteCount).append("type", mDiscreteType)
                .toString();
    }
}
