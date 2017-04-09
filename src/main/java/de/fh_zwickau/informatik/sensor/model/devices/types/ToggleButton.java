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
 * The {@link ToggleButton} represents a switch binary from ZAutomation API. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class ToggleButton extends Device {
    @Override
    public String on() {
        return mCommandHandler.on(this);
    }

    @Override
    public String off() {
        return mCommandHandler.off(this);
    }

    @Override
    public String exact(String level) {
        return mCommandHandler.exact(this, level);
    }

    @Override
    public String upstart() {
        return mCommandHandler.upstart(this);
    }

    @Override
    public String upstop() {
        return mCommandHandler.upstop(this);
    }

    @Override
    public String downstart() {
        return mCommandHandler.downstart(this);
    }

    @Override
    public String downstop() {
        return mCommandHandler.downstop(this);
    }

    @Override
    public String update() {
        return mCommandHandler.update(this);
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.model.devices.Device#toString()
     */
    @Override
    public String toString() {
        return "ToggleButton [ " + super.toString() + " ]";
    }
}
