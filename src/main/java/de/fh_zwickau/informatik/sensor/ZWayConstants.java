/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor;

/**
 * The {@link ZWayConstants} class defines ZAutomation constants. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class ZWayConstants {
    public static final String PATH_LOGIN = "login";
    public static final String PATH_STATUS = "status";
    public static final String PATH_NAMESPACES = "namespaces";
    public static final String PATH_MODULES = "modules";
    public static final String PATH_INSTANCES = "instances";
    public static final String PATH_DEVICES = "devices";
    public static final String PATH_DEVICES_COMMAND = "devices/{vDevName}/command/{command}";
    public static final String PATH_LOCATIONS = "locations";
    public static final String PATH_PROFILES = "profiles";
    public static final String PATH_NOTIFICATIONS = "notifications";
    public static final String PATH_DEVICE_HISTORY = "history";

    public static final String ZWAVE_PATH_DEVICES = "devices[{nodeId}]";

    public static final String ICON_NONE = "";
    public static final String ICON_SWITCH = "switch";
    public static final String ICON_MULTILEVEL = "multilevel";
    public static final String ICON_SMOKE = "smoke";
    public static final String ICON_CO = "co";
    public static final String ICON_COOLING = "cooling";
    public static final String ICON_DOOR = "door";
    public static final String ICON_MOTION = "motion";
    public static final String ICON_FLOOD = "flood";
    public static final String ICON_TEMPERATURE = "temperature";
    public static final String ICON_LUMINOSITY = "luminosity";
    public static final String ICON_ENERGY = "energy";
    public static final String ICON_HUMIDITY = "humidity";
    public static final String ICON_BATTERY = "battery";
    public static final String ICON_THERMOSTAT = "thermostat";
    public static final String ICON_FAN = "fan";
    public static final String ICON_METER = "meter";

    public static final String PROBE_TYPE_TEMPERATURE = "temperature";
    public static final String PROBE_TYPE_LUMINOSITY = "luminosity";
    public static final String PROBE_TYPE_HUMIDITY = "humidity";
    public static final String PROBE_TYPE_ULTRAVIOLET = "ultraviolet";
    public static final String PROBE_TYPE_GENERAL_PURPOSE = "general_purpose";
    public static final String PROBE_TYPE_ALARM_BURGLAR = "alarm_burglar";
    public static final String PROBE_TYPE_ENERGY = "energy";
    // TODO meterElectric_kilowatt_per_hour ignored ... metrics.probeTitle will be used
    // TODO meterElectric_watt ignored ... metrics.probeTitle will be used
    // TODO alarmSensor_general_purpose
    // TODO thermostat_mode
    // TODO thermostat_set_point

    public static final String PROBE_TITLE_BATTERY = "Battery";
    public static final String PROBE_TITLE_GENERAL_PURPOSE = "General purpose";
    public static final String PROBE_TITLE_TEMPERATURE = "Temperature";
    public static final String PROBE_TITLE_LUMINISCENCE = "Luminiscence";
    public static final String PROBE_TITLE_HUMIDITY = "Humidity";
    public static final String PROBE_TITLE_ULTRAVIOLET = "Ultraviolet";
    public static final String PROBE_TITLE_POWER = "Power";
    public static final String PROBE_TITLE_ELECTRIC = "Electric"; // ! scaleTitles: W, kWh
    public static final String PROBE_TITLE_CO2_LEVEL = "CO2 Level";

    public static final String DEVICE_TYPE_BATTERY = "battery";
    public static final String DEVICE_TYPE_DOORLOCK = "doorlock";
    public static final String DEVICE_TYPE_THERMOSTAT = "thermostat";
    public static final String DEVICE_TYPE_SWITCH_BINARY = "switchBinary";
    public static final String DEVICE_TYPE_SWITCH_MULTILEVEL = "switchMultilevel";
    public static final String DEVICE_TYPE_SWITCH_MULTILEVEL_BLINDS = "switchMultilevel(Blinds)"; // TODO
                                                                                                  // http://docs.zwayhomeautomation.apiary.io/#reference/devices/device
    public static final String DEVICE_TYPE_SENSOR_BINARY = "sensorBinary";
    public static final String DEVICE_TYPE_SENSOR_MULTILEVEL = "sensorMultilevel";
    public static final String DEVICE_TYPE_SWITCH_TOGGLE = "switchToggle";

    public static final String DEVICE_TYPE_SWITCH_CONTROL = "switchControl";
}