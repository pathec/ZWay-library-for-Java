/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor.model.devices;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * The {@link DeviceCommand} represents a command for ZAutomation API. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class DeviceCommand {
    private String mDeviceId;
    private String mCommand;
    private Map<String, String> mParams;

    /**
     * Instantiate a command.
     *
     * @param deviceId ZAutomation device id
     * @param command ZAutamation command name
     * @param params parameter map
     */
    public DeviceCommand(String deviceId, String command, Map<String, String> params) {
        setDeviceId(deviceId);
        setCommand(command);
        mParams = params;
    }

    /**
     * Instantiate a command without parameter.
     *
     * @param deviceId ZAutomation device id
     * @param command ZAutamation command name
     */
    public DeviceCommand(String deviceId, String command) {
        setDeviceId(deviceId);
        setCommand(command);
        mParams = null;
    }

    public String getDeviceId() {
        return mDeviceId;
    }

    public void setDeviceId(String deviceId) {
        if (deviceId == null || deviceId.isEmpty()) {
            throw new IllegalArgumentException("device id is required");
        }

        mDeviceId = deviceId;
    }

    public String getCommand() {
        return mCommand;
    }

    public void setCommand(String command) {
        if (command == null || command.isEmpty()) {
            throw new IllegalArgumentException("command is required");
        }

        mCommand = command;
    }

    public Map<String, String> getParams() {
        return mParams;
    }

    public void setParams(Map<String, String> params) {
        mParams = params;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this);
        for (Entry<String, String> entry : mParams.entrySet()) {
            toStringBuilder.append(entry.getKey(), entry.getValue());
        }

        return new ToStringBuilder(this).append("deviceId", mDeviceId).append("command", mCommand)
                .append("params", toStringBuilder.toString()).toString();
    }
}
