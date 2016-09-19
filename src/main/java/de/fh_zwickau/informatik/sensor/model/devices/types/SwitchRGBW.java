/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor.model.devices.types;

import de.fh_zwickau.informatik.sensor.model.devices.Device;

/**
 * The {@link SwitchRGBW} represents a color switch from ZAutomation API. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class SwitchRGBW extends Device {
    @Override
    public String on() {
        return mCommandHandler.on(this);
    }

    @Override
    public String off() {
        return mCommandHandler.off(this);
    }

    @Override
    public String exact(Integer red, Integer green, Integer blue) {
        return mCommandHandler.exact(this, red, green, blue);
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.model.devices.Device#toString()
     */
    @Override
    public String toString() {
        return "SwitchBinary [ " + super.toString() + " ]";
    }
}
