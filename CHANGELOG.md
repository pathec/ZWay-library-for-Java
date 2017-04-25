# Change Log
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/)
and this project adheres to [Semantic Versioning](http://semver.org/).

## [0.2.7] - 2017-04-09
### Added
- Sensor discrete device type
- Sensor multiline device type
- Z-Way API function (getDeviceAsJson) to load a device as JSON, for special devices

## [0.2.6] - 2017-04-02
### Added
- Device history API for single device

## [0.2.5] - 2017-02-04
### Changed
- Instance API, id parameter from string to integer
- Device API, remove post device (not supported and add put device

### Added
- Add dummy device instance model
- Add GET (single instance), POST, DELETE for Instance API
- Add GET (single device), PUT for Device API

## [0.2.4] - 2017-02-03
### Added
- Add custom icons property to device model

## [0.2.3] - 2017-02-02
### Added
- Camera device type
- Text device type
- Icon API, post custom icon and load list of icons 

## [0.2.2] - 2017-01-30
### Changed
- Notification id from integer to long (Z-Way version 27.01.2017 v2.3.0) 

## [0.2.1] - 2017-01-27
### Added
- Device history API

## [0.2.0] - 2017-01-16
### Added
- Profile API
- Add function for getting current profile

### Fixed
- Handling of different notification types

## [0.1.9] - 2017-01-09
### Added
- User image field to location
- Notification API
- Remote access, for example with find.z-wave.me as intermediary

### Changed
- Add since parameter to notification API

### Fixed
- Add getter and setter for device tags

## [0.1.8] - 2016-12-22
### Added
- WebSocket support

## [0.1.7] - 2016-12-07
### Added
- Support for command class ThermostatMode (ZWaveAPI)

## [0.1.6] - 2016-11-14
### Removed
- Fields mode and modes from device model (have been removed in current version)
- Commands setMode() and setTemp() from device model and ZWayApi (have been removed in current version)

### Fixed
- Add command exact() to device type thermostat instead of setMode() and setTemp()

## [0.1.5] - 2016-10-07
### Added
- Method to update controller data, for example to enable or disable secure inclusion

## [0.1.4] - 2016-10-05
### Added
- ZWave controller API with controller information and inclusion/exclusion functions

## [0.1.3] - 2016-09-26
### Added
- Minimal implementation of location API (only with id and title property)
- Extend (physical) Z-Wave device model from ZWaveAPI

## [0.1.2] - 2016-09-25
### Fixed
- Exception handling if the HTTP status differs from 200, which is important in case that the server is restarted (HTTP status 401)
- Only the login function sets the instance in a non-valid state when an error occurs (callback with invalidate state true). All other functions inform only the caller.
- Ignore device type *text* to suppress the warning

### Added
- Logging information

## [0.1.1] - 2016-09-19
### Changed
- Command implementation of device types:
    - Battery
    - SwitchControl
    - SwitchMultilevel
- Device deserializer

### Added
- Device type SwitchRGBW
- Device type ToggleButton
- All possible probe types and icons
- All possible commands
- Extend metrics by color, min and max element

### Removed
- Device type SwitchMultilevelBlinds

## [0.1.0] - 2016-09-01
### Added
- General project structure with interfaces, types, ... from Z-Way API
- HTTP implementation of Z-Way API with following methods:
    - GET login
    - GET instances
    - PUT instance
    - GET devices
    - GET device command
    - GET Z-Wave device
