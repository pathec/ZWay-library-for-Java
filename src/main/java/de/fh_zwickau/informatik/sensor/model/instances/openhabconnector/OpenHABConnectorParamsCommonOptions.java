/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor.model.instances.openhabconnector;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.gson.annotations.SerializedName;

/**
 * The {@link OpenHABConnectorParamsCommonOptions} represents a part of openHAB connector configuration. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class OpenHABConnectorParamsCommonOptions {
    @SerializedName("openHabServers")
    private List<OpenHabConnectorZWayServer> mOpenHabServers;

    /**
     * Instantiate a the common option part of openHAB connector configuration with default values.
     */
    public OpenHABConnectorParamsCommonOptions() {
        mOpenHabServers = new ArrayList<OpenHabConnectorZWayServer>();
    }

    public List<OpenHabConnectorZWayServer> getOpenHabServers() {
        return mOpenHabServers;
    }

    public void setOpenHabServers(List<OpenHabConnectorZWayServer> openHabServers) {
        this.mOpenHabServers = openHabServers;
    }

    /**
     * Adds server if not exist in configuration or update ip address and port if openHAB alias is the same.
     *
     * @param pServer openHAB server
     * @return updated or added
     */
    public boolean updateOpenHabServer(OpenHabConnectorZWayServer pServer) {
        boolean serverExist = false;
        boolean serverUpdated = false;

        for (OpenHabConnectorZWayServer server : mOpenHabServers) {
            if (server.equals(pServer)) {
                serverExist = true;
            } else if (server.getOpenHabAlias().equals(pServer.getOpenHabAlias())) {
                serverUpdated = true;

                server.setIpAddress(pServer.getIpAddress());
                server.setPort(pServer.getPort());
            }
        }

        if (!serverExist && !serverUpdated) {
            mOpenHabServers.add(pServer);
        }

        if (!serverExist || serverUpdated) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes server if exist.
     *
     * @param pServer openHAB server
     * @return removed
     */
    public boolean removeOpenHabServer(OpenHabConnectorZWayServer pServer) {
        boolean serverExist = false;

        for (OpenHabConnectorZWayServer server : mOpenHabServers) {
            if (server.equals(pServer)) {
                serverExist = true;
                break;
            }
        }

        if (serverExist) {
            mOpenHabServers.remove(pServer);
            return true;
        }

        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("openHabServers", mOpenHabServers).toString();
    }
}
