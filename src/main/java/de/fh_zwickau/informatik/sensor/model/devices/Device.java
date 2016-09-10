/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor.model.devices;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.annotations.SerializedName;

/**
 * The {@link Device} represents a virtual device from ZAutomation API. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public abstract class Device {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @SerializedName("creationTime")
    private Integer mCreationTime;
    @SerializedName("creatorId")
    private Integer mCreatorId;
    @SerializedName("deviceType")
    private String mDeviceType;
    @SerializedName("h")
    private Integer mH;
    @SerializedName("hasHistory")
    private Boolean mHasHistory;
    @SerializedName("id")
    private String mDeviceId;
    @SerializedName("location")
    private Integer mLocation;
    @SerializedName("permanently_hidden")
    private Boolean mPermanentlyHidden;
    @SerializedName("probeType")
    private String mProbeType;
    @SerializedName("visibility")
    private Boolean mVisibility;
    @SerializedName("updateTime")
    private Integer mUpdateTime;

    @SerializedName("metrics")
    private Metrics mMetrics;
    @SerializedName("tags")
    private List<String> mTags;

    protected IDeviceCommands mCommandHandler;

    /**
     * Instantiate a device with default values.
     */
    public Device() {
        mCreationTime = -1;
        mCreatorId = -1;
        mDeviceType = "";
        mH = -1;
        mHasHistory = false;
        mDeviceId = "";
        mLocation = -1;
        mPermanentlyHidden = false;
        mProbeType = "";
        mVisibility = false;
        mUpdateTime = -1;

        mMetrics = new Metrics();
        mTags = new ArrayList<String>();
    }

    public Integer getCreationTime() {
        return mCreationTime;
    }

    public void setCreationTime(Integer creationTime) {
        this.mCreationTime = creationTime;
    }

    public Integer getCreatorId() {
        return mCreatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.mCreatorId = creatorId;
    }

    public String getDeviceType() {
        return mDeviceType;
    }

    public void setDeviceType(String deviceType) {
        this.mDeviceType = deviceType;
    }

    public Integer getH() {
        return mH;
    }

    public void setH(Integer h) {
        this.mH = h;
    }

    public Boolean getHasHistory() {
        return mHasHistory;
    }

    public void setHasHistory(Boolean hasHistory) {
        this.mHasHistory = hasHistory;
    }

    public String getDeviceId() {
        return mDeviceId;
    }

    public void setDeviceId(String deviceId) {
        this.mDeviceId = deviceId;
    }

    public Integer getLocation() {
        return mLocation;
    }

    public void setLocation(Integer location) {
        this.mLocation = location;
    }

    public Boolean getPermanentlyHidden() {
        return mPermanentlyHidden;
    }

    public void setPermanentlyHidden(Boolean permanently_hidden) {
        this.mPermanentlyHidden = permanently_hidden;
    }

    public String getProbeType() {
        return mProbeType;
    }

    public void setProbeType(String probeType) {
        this.mProbeType = probeType;
    }

    public Boolean getVisibility() {
        return mVisibility;
    }

    public void setVisibility(Boolean visability) {
        this.mVisibility = visability;
    }

    public Integer getUpdateTime() {
        return mUpdateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.mUpdateTime = updateTime;
    }

    public Metrics getMetrics() {
        return mMetrics;
    }

    public void setMetrics(Metrics metrics) {
        this.mMetrics = metrics;
    }

    /**
     * Extract the node id from virtual device id.
     * Example: ZWayVDev_13-0-49-5 - node id: 13
     *
     * @return node id
     */
    public Integer getNodeId() {
        // Check if virtual device comes with an associated device
        // ZWayVDev_[Node ID]-[Instance ID]-[Command Class ID]-[Scale ID] (see Z-Way Developers Documentation)
        // Example device id: ZWayVDev_13-0-49-5

        String regex = "(.*)_(\\d+)-(\\d)+-(\\d+)(.*)";

        if (this.mDeviceId.matches(regex)) {
            logger.debug("Device comes with an associated device.");

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(this.mDeviceId);
            if (matcher.matches()) {
                try {
                    return Integer.parseInt(matcher.group(2));
                } catch (NumberFormatException e) {
                    logger.warn("Couldn't match a node id (NumberFormatException).");
                }
            } else {
                logger.warn("Couldn't match a node id.");
            }
        } else {
            logger.debug("Device is only a virtual device.");
        }

        return -1;
    }

    /**
     * Set a Z-Way command handler.
     *
     * @param commandHandler
     */
    public void setCommandHandler(IDeviceCommands commandHandler) {
        mCommandHandler = commandHandler;
    }

    // available commands ...
    public String on() {
        throw new UnsupportedOperationException("Operation not supported by device type " + mDeviceType);
    }

    public String off() {
        throw new UnsupportedOperationException("Operation not supported by device type " + mDeviceType);
    }

    public String update() {
        throw new UnsupportedOperationException("Operation not supported by device type " + mDeviceType);
    }

    public String min() {
        throw new UnsupportedOperationException("Operation not supported by device type " + mDeviceType);
    }

    public String max() {
        throw new UnsupportedOperationException("Operation not supported by device type " + mDeviceType);
    }

    public String exact(String level) {
        throw new UnsupportedOperationException("Operation not supported by device type " + mDeviceType);
    }

    public String up() {
        throw new UnsupportedOperationException("Operation not supported by device type " + mDeviceType);
    }

    public String down() {
        throw new UnsupportedOperationException("Operation not supported by device type " + mDeviceType);
    }

    public String upMax() {
        throw new UnsupportedOperationException("Operation not supported by device type " + mDeviceType);
    }

    public String startUp() {
        throw new UnsupportedOperationException("Operation not supported by device type " + mDeviceType);
    }

    public String startDown() {
        throw new UnsupportedOperationException("Operation not supported by device type " + mDeviceType);
    }

    public String stop() {
        throw new UnsupportedOperationException("Operation not supported by device type " + mDeviceType);
    }

    public String open() {
        throw new UnsupportedOperationException("Operation not supported by device type " + mDeviceType);
    }

    public String close() {
        throw new UnsupportedOperationException("Operation not supported by device type " + mDeviceType);
    }

    public String setTemp(Double temp) {
        throw new UnsupportedOperationException("Operation not supported by device type " + mDeviceType);
    }

    public String setMode(String mode) {
        throw new UnsupportedOperationException("Operation not supported by device type " + mDeviceType);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("creationTime", mCreationTime).append("creatorId", mCreatorId)
                .append("deviceType", mDeviceType).append("h", mH).append("hasHistory", mHasHistory)
                .append("deviceId", mDeviceId).append("location", mLocation)
                .append("permanentlyHidden", mPermanentlyHidden).append("probeType", mProbeType)
                .append("visibility", mVisibility).append("updateTime", mUpdateTime).append("metrics", mMetrics)
                .append("tags", mTags).toString();
    }
}
