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
 * The {@link IconsLevel} represents the custom icons property of devices for level specific icons. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class IconsLevel {
    @SerializedName("open")
    private String mOpen;
    @SerializedName("closed")
    private String mClosed;
    @SerializedName("down")
    private String mDown;
    @SerializedName("half")
    private String mHalf;
    @SerializedName("up")
    private String mUp;
    @SerializedName("on")
    private String mOn;
    @SerializedName("off")
    private String mOff;

    // Gesture
    @SerializedName("press")
    private String mPress;
    @SerializedName("hold")
    private String mHold;
    @SerializedName("release")
    private String mRelease;
    @SerializedName("swipe_up")
    private String mSwipeUp;
    @SerializedName("swipe_down")
    private String mSwipeDown;
    @SerializedName("swipe_left")
    private String mSwipeLeft;
    @SerializedName("swipe_right")
    private String mSwipeRight;
    @SerializedName("swipe_top_left_to_bottom_right")
    private String mSwipeTopLeftToBottomRight;
    @SerializedName("swipe_top_right_to_bottom_right")
    private String mSwipeTopRightToBottomLeft;
    @SerializedName("swipe_bottom_left_to_top_right")
    private String mSwipeBottomLeftToTopRight;
    @SerializedName("swipe_bottom_right_to_top_left")
    private String mSwipeBottomRightToTopLeft;

    /**
     * Instantiate a icons object for level specific icons with default values.
     */
    public IconsLevel() {
        mOpen = "";
        mClosed = "";
        mDown = "";
        mUp = "";
        mOn = "";
        mOff = "";

        mPress = "";
        mHold = "";
        mRelease = "";
        mSwipeUp = "";
        mSwipeDown = "";
        mSwipeLeft = "";
        mSwipeRight = "";
        mSwipeTopLeftToBottomRight = "";
        mSwipeTopRightToBottomLeft = "";
        mSwipeBottomLeftToTopRight = "";
        mSwipeBottomRightToTopLeft = "";
    }

    public String getOpen() {
        if (mOpen == null) {
            mOpen = "";
        }
        return mOpen;
    }

    public void setOpen(String open) {
        this.mOpen = open;
    }

    public String getClosed() {
        if (mClosed == null) {
            mClosed = "";
        }
        return mClosed;
    }

    public void setClosed(String closed) {
        this.mClosed = closed;
    }

    public String getDown() {
        if (mDown == null) {
            mDown = "";
        }
        return mDown;
    }

    public void setDown(String down) {
        this.mDown = down;
    }

    public String getHalf() {
        if (mHalf == null) {
            mHalf = "";
        }
        return mHalf;
    }

    public void setHalf(String half) {
        this.mHalf = half;
    }

    public String getUp() {
        if (mUp == null) {
            mUp = "";
        }
        return mUp;
    }

    public void setUp(String up) {
        this.mUp = up;
    }

    public String getOn() {
        if (mOn == null) {
            mOn = "";
        }
        return mOn;
    }

    public void setOn(String on) {
        this.mOn = on;
    }

    public String getOff() {
        if (mOff == null) {
            mOff = "";
        }
        return mOff;
    }

    public void setOff(String off) {
        this.mOff = off;
    }

    public String getPress() {
        if (mPress == null) {
            mPress = "";
        }
        return mPress;
    }

    public void setPress(String press) {
        this.mPress = press;
    }

    public String getHold() {
        if (mHold == null) {
            mHold = "";
        }
        return mHold;
    }

    public void setHold(String hold) {
        this.mHold = hold;
    }

    public String getRelease() {
        if (mRelease == null) {
            mRelease = "";
        }
        return mRelease;
    }

    public void setRelease(String release) {
        this.mRelease = release;
    }

    public String getSwipeUp() {
        if (mSwipeUp == null) {
            mSwipeUp = "";
        }
        return mSwipeUp;
    }

    public void setSwipeUp(String swipeUp) {
        this.mSwipeUp = swipeUp;
    }

    public String getSwipeDown() {
        if (mSwipeDown == null) {
            mSwipeDown = "";
        }
        return mSwipeDown;
    }

    public void setSwipeDown(String swipeDown) {
        this.mSwipeDown = swipeDown;
    }

    public String getSwipeLeft() {
        if (mSwipeLeft == null) {
            mSwipeLeft = "";
        }
        return mSwipeLeft;
    }

    public void setSwipeLeft(String swipeLeft) {
        this.mSwipeLeft = swipeLeft;
    }

    public String getSwipeRight() {
        if (mSwipeRight == null) {
            mSwipeRight = "";
        }
        return mSwipeRight;
    }

    public void setSwipeRight(String swipeRight) {
        this.mSwipeRight = swipeRight;
    }

    public String getSwipeTopLeftToBottomRight() {
        if (mSwipeTopLeftToBottomRight == null) {
            mSwipeTopLeftToBottomRight = "";
        }
        return mSwipeTopLeftToBottomRight;
    }

    public void setSwipeTopLeftToBottomRight(String swipeTopLeftToBottomRight) {
        this.mSwipeTopLeftToBottomRight = swipeTopLeftToBottomRight;
    }

    public String getSwipeTopRightToBottomLeft() {
        if (mSwipeTopRightToBottomLeft == null) {
            mSwipeTopRightToBottomLeft = "";
        }
        return mSwipeTopRightToBottomLeft;
    }

    public void setSwipeTopRightToBottomLeft(String swipeTopRightToBottomLeft) {
        this.mSwipeTopRightToBottomLeft = swipeTopRightToBottomLeft;
    }

    public String getSwipeBottomLeftToTopRight() {
        if (mSwipeBottomLeftToTopRight == null) {
            mSwipeBottomLeftToTopRight = "";
        }
        return mSwipeBottomLeftToTopRight;
    }

    public void setSwipeBottomLeftToTopRight(String swipeBottomLeftToTopRight) {
        this.mSwipeBottomLeftToTopRight = swipeBottomLeftToTopRight;
    }

    public String getSwipeBottomRightToTopLeft() {
        if (mSwipeBottomRightToTopLeft == null) {
            mSwipeBottomRightToTopLeft = "";
        }
        return mSwipeBottomRightToTopLeft;
    }

    public void setSwipeBottomRightToTopLeft(String swipeBottomRightToTopLeft) {
        this.mSwipeBottomRightToTopLeft = swipeBottomRightToTopLeft;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("open", mOpen).append("closed", mClosed).append("down", mDown)
                .append("half", mHalf).append("up", mUp).append("on", mOn).append("off", mOff).append("press", mPress)
                .append("hold", mHold).append("release", mRelease).append("swipe_up", mSwipeUp)
                .append("swipe_down", mSwipeDown).append("swipe_left", mSwipeLeft).append("swipe_right", mSwipeRight)
                .append("swipe_top_left_to_bottom_right", mSwipeTopLeftToBottomRight)
                .append("swipe_top_right_to_bottom_right", mSwipeTopRightToBottomLeft)
                .append("swipe_bottom_left_to_top_right", mSwipeBottomLeftToTopRight)
                .append("swipe_bottom_right_to_top_left", mSwipeBottomRightToTopLeft).toString();
    }
}
