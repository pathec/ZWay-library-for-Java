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
 * The {@link OpenHabConnectorZWayServer} represents the Z-Way server of openHAB connector. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class OpenHabConnectorZWayServer {
    @SerializedName("openHabAlias")
    private String mOpenHabAlias;
    @SerializedName("ipAddress")
    private String mIpAddress;
    @SerializedName("port")
    private Integer mPort;

    /**
     * Instantiate a Z-Way server with default values.
     */
    public OpenHabConnectorZWayServer() {
        mOpenHabAlias = "";
        mIpAddress = "";
        mPort = -1;
    }

    /**
     * Instantiate a Z-Way server with passed values.
     *
     * @param openHabAlias
     * @param ipAddress
     * @param port
     */
    public OpenHabConnectorZWayServer(String openHabAlias, String ipAddress, Integer port) {
        if (openHabAlias == null || openHabAlias.isEmpty()) {
            throw new IllegalArgumentException("openHAB alias is required");
        }
        if (ipAddress == null || ipAddress.isEmpty()) {
            throw new IllegalArgumentException("ip address is required");
        }
        if (port == null) {
            throw new IllegalArgumentException("port is required");
        }

        mOpenHabAlias = openHabAlias;
        mIpAddress = ipAddress;
        mPort = port;
    }

    public String getOpenHabAlias() {
        if (mOpenHabAlias == null) {
            mOpenHabAlias = "";
        }
        return mOpenHabAlias;
    }

    public void setOpenHabAlias(String openHabAlias) {
        if (openHabAlias == null || openHabAlias.isEmpty()) {
            throw new IllegalArgumentException("openHAB alias is required");
        }

        this.mOpenHabAlias = openHabAlias;
    }

    public String getIpAddress() {
        if (mIpAddress == null) {
            mIpAddress = "";
        }
        return mIpAddress;
    }

    public void setIpAddress(String ipAddress) {
        if (ipAddress == null || ipAddress.isEmpty()) {
            throw new IllegalArgumentException("ip address is required");
        }

        this.mIpAddress = ipAddress;
    }

    public Integer getPort() {
        if (mPort == null) {
            mPort = -1;
        }
        return mPort;
    }

    public void setPort(Integer port) {
        if (port == null) {
            throw new IllegalArgumentException("port is required");
        }

        this.mPort = port;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("openHabAlias", mOpenHabAlias).append("ipAddress", mIpAddress)
                .append("port", mPort).toString();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((mIpAddress == null) ? 0 : mIpAddress.hashCode());
        result = prime * result + ((mOpenHabAlias == null) ? 0 : mOpenHabAlias.hashCode());
        result = prime * result + ((mPort == null) ? 0 : mPort.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        OpenHabConnectorZWayServer other = (OpenHabConnectorZWayServer) obj;
        if (mIpAddress == null) {
            if (other.mIpAddress != null) {
                return false;
            }
        } else if (!mIpAddress.equals(other.mIpAddress)) {
            return false;
        }
        if (mOpenHabAlias == null) {
            if (other.mOpenHabAlias != null) {
                return false;
            }
        } else if (!mOpenHabAlias.equals(other.mOpenHabAlias)) {
            return false;
        }
        if (mPort == null) {
            if (other.mPort != null) {
                return false;
            }
        } else if (!mPort.equals(other.mPort)) {
            return false;
        }
        return true;
    }
}
