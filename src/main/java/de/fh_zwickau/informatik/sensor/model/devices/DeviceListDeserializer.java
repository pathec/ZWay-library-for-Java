/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor.model.devices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import de.fh_zwickau.informatik.sensor.ZWayConstants;
import de.fh_zwickau.informatik.sensor.model.devices.types.Battery;
import de.fh_zwickau.informatik.sensor.model.devices.types.Doorlock;
import de.fh_zwickau.informatik.sensor.model.devices.types.SensorBinary;
import de.fh_zwickau.informatik.sensor.model.devices.types.SensorMultilevel;
import de.fh_zwickau.informatik.sensor.model.devices.types.SwitchBinary;
import de.fh_zwickau.informatik.sensor.model.devices.types.SwitchControl;
import de.fh_zwickau.informatik.sensor.model.devices.types.SwitchMultilevel;
import de.fh_zwickau.informatik.sensor.model.devices.types.SwitchRGBW;
import de.fh_zwickau.informatik.sensor.model.devices.types.SwitchToggle;
import de.fh_zwickau.informatik.sensor.model.devices.types.Thermostat;
import de.fh_zwickau.informatik.sensor.model.devices.types.ToggleButton;

/**
 * The {@link DeviceListDeserializer} build a device list with known, concrete devices based on JSON data. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class DeviceListDeserializer {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @param devicesAsJson list list as JSON data
     * @param commandHandler Z-Way command handler
     * @return device list
     */
    public DeviceList deserializeDeviceList(JsonArray devicesAsJson, IDeviceCommands commandHandler) {
        DeviceList deviceList = new DeviceList();
        Gson gson = new Gson();

        for (JsonElement deviceAsJson : devicesAsJson) {
            String deviceType = deviceAsJson.getAsJsonObject().get("deviceType").getAsString();
            Device device = null;
            switch (deviceType) {
                case ZWayConstants.DEVICE_TYPE_BATTERY:
                    device = gson.fromJson(deviceAsJson, Battery.class);
                    break;
                case ZWayConstants.DEVICE_TYPE_DOORLOCK:
                    device = gson.fromJson(deviceAsJson, Doorlock.class);
                    break;
                case ZWayConstants.DEVICE_TYPE_THERMOSTAT:
                    device = gson.fromJson(deviceAsJson, Thermostat.class);
                    break;
                case ZWayConstants.DEVICE_TYPE_SWITCH_BINARY:
                    device = gson.fromJson(deviceAsJson, SwitchBinary.class);
                    break;
                case ZWayConstants.DEVICE_TYPE_SWITCH_MULTILEVEL:
                    device = gson.fromJson(deviceAsJson, SwitchMultilevel.class);
                    break;
                case ZWayConstants.DEVICE_TYPE_SENSOR_BINARY:
                    device = gson.fromJson(deviceAsJson, SensorBinary.class);
                    break;
                case ZWayConstants.DEVICE_TYPE_SENSOR_MULTILEVEL:
                    device = gson.fromJson(deviceAsJson, SensorMultilevel.class);
                    break;
                case ZWayConstants.DEVICE_TYPE_SWITCH_TOGGLE:
                    device = gson.fromJson(deviceAsJson, SwitchToggle.class);
                    break;
                case ZWayConstants.DEVICE_TYPE_SWITCH_RGBW:
                    device = gson.fromJson(deviceAsJson, SwitchRGBW.class);
                    break;
                case ZWayConstants.DEVICE_TYPE_SWITCH_CONTROL:
                    device = gson.fromJson(deviceAsJson, SwitchControl.class);
                    break;
                case ZWayConstants.DEVICE_TYPE_TOGGLE_BUTTON:
                    device = gson.fromJson(deviceAsJson, ToggleButton.class);
                    break;
                default:
                    logger.warn("Unknown device type: " + deviceType);
                    break;
            }

            if (device != null) {
                device.setCommandHandler(commandHandler);
                deviceList.addDevice(device);
            }
        }

        return deviceList;
    }
}
