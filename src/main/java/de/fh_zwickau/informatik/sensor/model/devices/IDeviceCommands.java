/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor.model.devices;

/**
 * The {@link IDeviceCommands} defines all possible commands from ZAutomation API. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public interface IDeviceCommands {
    String on(Device device);

    String off(Device device);

    String update(Device device);

    String min(Device device);

    String max(Device device);

    String exact(Device device, String level);

    String up(Device device);

    String down(Device device);

    String upMax(Device device);

    String startUp(Device device);

    String startDown(Device device);

    String stop(Device device);

    String open(Device device);

    String close(Device device);

    String setTemp(Device device, Double temp);

    String setMode(Device device, String mode);
}
