# Z-Way library for Java

This project provides a Z-Way library for Java. The offical API description is available under http://docs.zwayhomeautomation.apiary.io/#. Currently there is only one implementation which uses HTTP with a subset of API functions (other implementations are planned, such as MQTT or WebSockets). Available functions:

- GET login
- GET instances
- PUT instance
- GET notifications
- GET profiles
- GET devices
- GET device command
- GET locations (only with id, title and image property)
- GET device history
- GET icons
- POST icon
- GET Z-Wave device (device model not complete)
- other methods throw an `UnsupportedOperationException`

## Installation

- Install Maven if not already installed
- Clone project and navigate with console to the root folder of the project
- Build JAR file: `mvn package`
- Add the resulting file (`target/zway-lib-*.*.*-SNAPSHOT.jar`) into your project
- All dependencies: Gson, Apache Commons Lang, Jetty and SLF4J must be resolved (see [Dependencies](#dependencies) for detail)

## Usage

- Add library to your project ([Installation](#installation))
- Choose a implementation for your project (currently there is only a HTTP implementation available `ZWayApiHttp`)
- Create a instance of API: `IZWayApi mZWayApi = new ZWayApiHttp(ipAddress, port, protocol, username, password, remoteId, useRemoteService, caller);`
 - IP address: Z-Way server address
 - port: Z-Way server port (default: 8083)
 - protocol: http/https
 - username (default: admin)
 - password (default: admin)
 - remote id
 - use remote service: true/false enables another login implementation
 - caller must implement the `IZWayApiCallbacks` interface to receive asynchronous callbacks

### GET login
- `mZWayApi.getLogin()` returns the session id or null (which is stored internaly for further requests)

### GET devices

- Synchronous: ```mZWayApi.getDevices()``` returns the device list or null
- Asynchronous:

    ```getDevices(new IZWayCallback<DeviceList>() {
        @Override
        public void onSuccess(DeviceList deviceList) {
        }
    });```
- `DeviceList` provides different ways to access the devices. Only devices which don't represent physical devices or only devices which do represent physical devices.
 - `getDevices()` returns a list with virtual devices which don't represent physical devices in Z-Wave network
 - `getDevicesGroupByNodeId()` returns a map (node id, device list) of virtual devices associated with a physical device
 
### Command Class ThermostatMode
- Load Z-Wave device: `api.getZWaveDevice(42)`
- Get current thermostat mode: `device.getInstances().get0().getCommandClass().get64().getData().getMode().getValue()`
- Get possible modes: `device.getInstances().get0().getCommandClass().get64().getThermostatModes()`
- Set thermostat mode: `api.getZWaveDeviceThermostatModeSet(42, 1)`

## Supported device types

- Battery
- Doorlock
- SensorBinary
- SensorMultilevel
- SwitchBinary
- SwitchControl
- SwitchMultilevel
- SwitchRGBW
- SwitchToggle
- Thermostat
- ToggleButton
- Text
- Camera

## Dependencies

- Gson (com.google.code.gson:gson:2.4 - https://github.com/google/gson)
- Apache Commons Lang (org.apache.commons:commons-lang3:3.4 - http://commons.apache.org/proper/commons-lang/)
- Jetty :: Asynchronous HTTP Client (org.eclipse.jetty:jetty-client:9.3.11.v20160721 - http://www.eclipse.org/jetty)
- Jetty :: Http Utility (org.eclipse.jetty:jetty-http:9.3.11.v20160721 - http://www.eclipse.org/jetty)
- Jetty :: IO Utility (org.eclipse.jetty:jetty-io:9.3.11.v20160721 - http://www.eclipse.org/jetty)
- Jetty :: Utilities (org.eclipse.jetty:jetty-util:9.3.11.v20160721 - http://www.eclipse.org/jetty)
- Jetty :: WebSocket :: API (org.eclipse.jetty.websocket:websocket-api:9.3.12.v20160915 - http://www.eclipse.org/jetty)
- Jetty :: WebSocket :: Client (org.eclipse.jetty.websocket:websocket-client:9.3.12.v20160915 - http://www.eclipse.org/jetty)
- Jetty :: WebSocket :: Common (org.eclipse.jetty.websocket:websocket-common:9.3.12.v20160915 - http://www.eclipse.org/jetty)
- SLF4J API Module (org.slf4j:slf4j-api:1.7.21 - http://www.slf4j.org)

## Contributing

1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request :D

## License

Copyright (C) 2016 by [Software-Systementwicklung Zwickau](http://www.software-systementwicklung.de/) Research Group

All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v10.html

This project uses 3rd party tools. You can find the list of 3rd party tools including their authors and licenses [here](LICENSE-3RD-PARTY.txt).

<br>
<img src="doc/BMWi_4C_Gef_en.jpg" width="200">
