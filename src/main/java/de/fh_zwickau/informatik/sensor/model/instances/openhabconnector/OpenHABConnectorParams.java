/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor.model.instances.openhabconnector;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.gson.annotations.SerializedName;

/**
 * The {@link OpenHABConnectorParams} represents a part of openHAB connector configuration. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class OpenHABConnectorParams {
    @SerializedName("commonOptions")
    private OpenHABConnectorParamsCommonOptions mCommonOptions;

    /**
     * Instantiate a the params part of openHAB connector configuration with default values.
     */
    public OpenHABConnectorParams() {
        mCommonOptions = new OpenHABConnectorParamsCommonOptions();
    }

    public OpenHABConnectorParamsCommonOptions getCommonOptions() {
        if (mCommonOptions == null) {
            mCommonOptions = new OpenHABConnectorParamsCommonOptions();
        }
        return mCommonOptions;
    }

    public void setCommonOptions(OpenHABConnectorParamsCommonOptions commonOptions) {
        this.mCommonOptions = commonOptions;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("commonOptions", mCommonOptions).toString();
    }
}
