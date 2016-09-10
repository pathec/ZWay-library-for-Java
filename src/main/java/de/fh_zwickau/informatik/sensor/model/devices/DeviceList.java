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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * The {@link DeviceList} represents a device list from ZAutomation API. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class DeviceList {
    private List<Device> mDevices;

    public DeviceList() {
        mDevices = new ArrayList<Device>();
    }

    public void addDevice(Device device) {
        mDevices.add(device);
    }

    /**
     * @return list of virtual devices
     */
    public List<Device> getDevices() {
        ArrayList<Device> virtualDevices = new ArrayList<Device>();

        for (Device device : mDevices) {
            Integer nodeId = device.getNodeId();
            if (nodeId == -1) {
                virtualDevices.add(device);
            }
        }

        return virtualDevices;
    }

    /**
     * Return the first occurrence of device
     *
     * @param deviceId ZAutomation device id
     * @return virtual device
     */
    public Device getDeviceById(String deviceId) {

        for (Device device : mDevices) {
            if (device.getDeviceId().equals(deviceId)) {
                return device;
            }
        }
        return null;
    }

    /**
     * @return list of physical devices with associated virtual devices
     */
    public Map<Integer, List<Device>> getDevicesGroupByNodeId() {
        Map<Integer, List<Device>> physicalDevices = new HashMap<Integer, List<Device>>();

        for (Device device : mDevices) {
            Integer nodeId = device.getNodeId();
            if (nodeId != -1) {
                if (physicalDevices.containsKey(nodeId)) {
                    physicalDevices.get(nodeId).add(device);
                } else {
                    ArrayList<Device> devices = new ArrayList<Device>();
                    devices.add(device);

                    physicalDevices.put(nodeId, devices);
                }
            }
        }

        return physicalDevices;
    }

    /**
     * @param nodeId Z-Wave device node id
     * @return list of physical devices with associated virtual devices
     */
    public Map<Integer, List<Device>> getDevicesByNodeId(Integer nodeId) {
        Map<Integer, List<Device>> physicalDevice = new HashMap<Integer, List<Device>>();

        for (Device device : mDevices) {
            if (nodeId == device.getNodeId()) {
                if (physicalDevice.containsKey(nodeId)) {
                    physicalDevice.get(nodeId).add(device);
                } else {
                    ArrayList<Device> devices = new ArrayList<Device>();
                    devices.add(device);

                    physicalDevice.put(nodeId, devices);
                }
            }
        }

        return physicalDevice;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this);
        for (Device device : mDevices) {
            toStringBuilder.append(device);
        }

        return new ToStringBuilder(this).append("deviceList", toStringBuilder.toString()).toString();
    }
}
