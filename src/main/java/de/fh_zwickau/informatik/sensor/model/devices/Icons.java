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
 * The {@link Icons} represents the custom icons property of devices. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class Icons {
    @SerializedName("default")
    private String mDefault;
    @SerializedName("level")
    private IconsLevel mIconsLevel;

    /**
     * Instantiate a icons object with default values.
     */
    public Icons() {
        mDefault = "";
        mIconsLevel = new IconsLevel();
    }

    public String getDefault() {
        if (mDefault == null) {
            mDefault = "";
        }
        return mDefault;
    }

    public void setDefault(String defaultIcon) {
        this.mDefault = defaultIcon;
    }

    public IconsLevel getIconsLevel() {
        if (mIconsLevel == null) {
            mIconsLevel = new IconsLevel();
        }
        return mIconsLevel;
    }

    public void setIconsLevel(IconsLevel iconsLevel) {
        this.mIconsLevel = iconsLevel;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("default", mDefault).append("level", mIconsLevel).toString();
    }
}
