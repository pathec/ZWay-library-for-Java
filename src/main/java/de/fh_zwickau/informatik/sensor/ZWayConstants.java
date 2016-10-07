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
    public static final String ZWAVE_PATH_CONTROLLER = "controller";
    public static final String ZWAVE_PATH_INCLUSION = "controller.AddNodeToNetwork({flag})";
    public static final String ZWAVE_PATH_EXCLUSION = "controller.RemoveNodeFromNetwork({flag})";
    public static final String ZWAVE_PATH_CONTROLLER_DATA = "controller.data";

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
    public static final String ICON_BLINDS = "blinds";
    public static final String ICON_TAMPER = "tamper";
    public static final String ICON_BAROMETER = "barometer";
    public static final String ICON_ULTRAVIOLET = "ultraviolet";
    public static final String ICON_ALARM = "alarm";

    public static final String PROBE_TYPE_TEMPERATURE = "temperature";
    public static final String PROBE_TYPE_LUMINOSITY = "luminosity";
    public static final String PROBE_TYPE_HUMIDITY = "humidity";
    public static final String PROBE_TYPE_ULTRAVIOLET = "ultraviolet";
    public static final String PROBE_TYPE_GENERAL_PURPOSE = "general_purpose";
    public static final String PROBE_TYPE_ENERGY = "energy";
    public static final String PROBE_TYPE_MOTOR = "motor";
    public static final String PROBE_TYPE_MULTILEVEL = "multilevel";
    public static final String PROBE_TYPE_SMOKE = "smoke";
    public static final String PROBE_TYPE_CO = "co";
    public static final String PROBE_TYPE_FLOOD = "flood";
    public static final String PROBE_TYPE_COOLING = "cooling";
    public static final String PROBE_TYPE_TAMPER = "tamper";
    public static final String PROBE_TYPE_DOOR_WINDOW = "door-window";
    public static final String PROBE_TYPE_MOTION = "motion";
    public static final String PROBE_TYPE_BAROMETER = "barometer";

    public static final String PROBE_TYPE_METER_ELECTRIC_KILOWATT_PER_HOUR = "meterElectric_kilowatt_per_hour";
    public static final String PROBE_TYPE_METER_ELECTRIC_WATT = "meterElectric_watt";
    public static final String PROBE_TYPE_METER_ELECTRIC_PULSE_COUNT = "meterElectric_pulse_count";
    public static final String PROBE_TYPE_METER_ELECTRIC_VOLTAGE = "meterElectric_voltage";
    public static final String PROBE_TYPE_METER_ELECTRIC_AMPERE = "meterElectric_ampere";
    public static final String PROBE_TYPE_METER_ELECTRIC_POWER_FACTOR = "meterElectric_power_factor";

    public static final String PROBE_TYPE_BATTERY = "Battery"; // attention! uppercase

    public static final String PROBE_TYPE_ALARM_DOOR = "alarm_door";
    public static final String PROBE_TYPE_ALARM_SMOKE = "alarm_smoke";
    public static final String PROBE_TYPE_ALARM_CO = "alarm_co";
    public static final String PROBE_TYPE_ALARM_COO = "alarm_coo";
    public static final String PROBE_TYPE_ALARM_HEAT = "alarm_heat";
    public static final String PROBE_TYPE_ALARM_FLOOD = "alarm_flood";
    public static final String PROBE_TYPE_ALARM_BURGLAR = "alarm_burglar";
    public static final String PROBE_TYPE_ALARM_POWER = "alarm_power";
    public static final String PROBE_TYPE_ALARM_SYSTEM = "alarm_system";
    public static final String PROBE_TYPE_ALARM_EMERGENCY = "alarm_emergency";
    public static final String PROBE_TYPE_ALARM_CLOCK = "alarm_clock";

    // probe types for deprecated command class AlarmSensor

    public static final String PROBE_TYPE_ALARM_SENSOR_GENERAL_PURPOSE = "alarmSensor_door";
    public static final String PROBE_TYPE_ALARM_SENSOR_SMOKE = "alarmSensor_smoke";
    public static final String PROBE_TYPE_ALARM_SENSOR_CO = "alarmSensor_co";
    public static final String PROBE_TYPE_ALARM_SENSOR_COO = "alarmSensor_coo";
    public static final String PROBE_TYPE_ALARM_SENSOR_HEAT = "alarmSensor_heat";
    public static final String PROBE_TYPE_ALARM_SENSOR_FLOOD = "alarmSensor_flood";
    public static final String PROBE_TYPE_ALARM_SENSOR_DOOR = "alarmSensor_door";
    public static final String PROBE_TYPE_ALARM_SENSOR_BURGLAR = "alarmSensor_burglar";
    public static final String PROBE_TYPE_ALARM_SENSOR_POWER = "alarmSensor_power";
    public static final String PROBE_TYPE_ALARM_SENSOR_SYSTEM = "alarmSensor_system";
    public static final String PROBE_TYPE_ALARM_SENSOR_EMERGENCY = "alarmSensor_emergency";
    public static final String PROBE_TYPE_ALARM_SENSOR_CLOCK = "alarmSensor_clock";

    // end - probe types for deprecated command class AlarmSensor

    public static final String PROBE_TYPE_SWITCH_COLOR_RGB = "switchColor_rgb";
    public static final String PROBE_TYPE_SWITCH_COLOR_SOFT_WHITE = "switchColor_soft_white";
    public static final String PROBE_TYPE_SWITCH_COLOR_COLD_WHITE = "switchColor_cold_white";
    public static final String PROBE_TYPE_SWITCH_COLOR_RED = "switchColor_red";
    public static final String PROBE_TYPE_SWITCH_COLOR_GREEN = "switchColor_green";
    public static final String PROBE_TYPE_SWITCH_COLOR_BLUE = "switchColor_blue";

    public static final String PROBE_TYPE_THERMOSTAT_MODE = "thermostat_mode";
    public static final String PROBE_TYPE_THERMOSTAT_SET_POINT = "thermostat_set_point";

    public static final String PROBE_TITLE_BATTERY = "Battery";
    public static final String PROBE_TITLE_GENERAL_PURPOSE = "General purpose";
    public static final String PROBE_TITLE_TEMPERATURE = "Temperature";
    public static final String PROBE_TITLE_LUMINISCENCE = "Luminiscence";
    public static final String PROBE_TITLE_HUMIDITY = "Humidity";
    public static final String PROBE_TITLE_ULTRAVIOLET = "Ultraviolet";
    public static final String PROBE_TITLE_POWER = "Power";
    public static final String PROBE_TITLE_ELECTRIC = "Electric"; // attention! scaleTitles: W, kWh
    public static final String PROBE_TITLE_CO2_LEVEL = "CO2 Level";

    public static final String DEVICE_TYPE_BATTERY = "battery";
    public static final String DEVICE_TYPE_DOORLOCK = "doorlock";
    public static final String DEVICE_TYPE_THERMOSTAT = "thermostat";
    public static final String DEVICE_TYPE_SWITCH_BINARY = "switchBinary";
    public static final String DEVICE_TYPE_SWITCH_MULTILEVEL = "switchMultilevel";
    public static final String DEVICE_TYPE_SENSOR_BINARY = "sensorBinary";
    public static final String DEVICE_TYPE_SENSOR_MULTILEVEL = "sensorMultilevel";
    public static final String DEVICE_TYPE_SWITCH_TOGGLE = "switchToggle";
    public static final String DEVICE_TYPE_SWITCH_CONTROL = "switchControl";
    public static final String DEVICE_TYPE_TOGGLE_BUTTON = "toggleButton";
    public static final String DEVICE_TYPE_SWITCH_RGBW = "switchRGBW";
    public static final String DEVICE_TYPE_TEXT = "text";
}