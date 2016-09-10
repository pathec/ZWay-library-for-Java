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

import de.fh_zwickau.informatik.sensor.model.instances.Instance;

/**
 * The {@link OpenHABConnector} represents the instance representation of openHAB connector. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class OpenHABConnector extends Instance {
    @SerializedName("params")
    private OpenHABConnectorParams mParams;

    /**
     * Instantiate a openHAB connector with default values.
     */
    public OpenHABConnector() {
        super();

        mParams = new OpenHABConnectorParams();
    }

    public OpenHABConnectorParams getParams() {
        return mParams;
    }

    public void setParams(OpenHABConnectorParams params) {
        this.mParams = params;
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.model.instances.Instance#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("instance", super.toString()).append("params", mParams).toString();
    }
}
