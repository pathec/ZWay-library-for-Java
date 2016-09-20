# Change Log
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/) 
and this project adheres to [Semantic Versioning](http://semver.org/).

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