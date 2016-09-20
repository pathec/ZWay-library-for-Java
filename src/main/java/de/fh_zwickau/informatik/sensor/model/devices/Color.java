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
 * The {@link Color} represents the color field of metrics with the components: red, green and blue. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class Color {
    @SerializedName("r")
    private Integer mRed;
    @SerializedName("g")
    private Integer mGreen;
    @SerializedName("b")
    private Integer mBlue;

    /**
     * Instantiate a color object with default values.
     */
    public Color() {
        mRed = 0;
        mGreen = 0;
        mBlue = 0;
    }

    public Integer getRed() {
        return mRed;
    }

    public void setRed(Integer red) {
        this.mRed = red;
    }

    public Integer getGreen() {
        return mGreen;
    }

    public void setGreen(Integer green) {
        this.mGreen = green;
    }

    public Integer getBlue() {
        return mBlue;
    }

    public void setBlue(Integer blue) {
        this.mBlue = blue;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("red", mRed).append("green", mGreen).append("blue", mBlue).toString();
    }
}
