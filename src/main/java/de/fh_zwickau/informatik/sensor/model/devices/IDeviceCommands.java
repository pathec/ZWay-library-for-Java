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

    String exactSmooth(Device device, String level, Integer duration);

    String exact(Device device, Integer red, Integer green, Integer blue);

    String up(Device device);

    String down(Device device);

    String upMax(Device device);

    String startUp(Device device);

    String startDown(Device device);

    String stop(Device device);

    String open(Device device);

    String close(Device device);

    String increase(Device device);

    String decrease(Device device);

    String upstart(Device device);

    String upstop(Device device);

    String downstart(Device device);

    String downstop(Device device);
}
