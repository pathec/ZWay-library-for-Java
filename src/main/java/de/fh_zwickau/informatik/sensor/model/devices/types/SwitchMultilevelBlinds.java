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
 * The {@link SwitchMultilevelBlinds} represents a switch multilevel (blinds) from ZAutomation API. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class SwitchMultilevelBlinds extends Device {
    @Override
    public String up() {
        return mCommandHandler.up(this);
    }

    @Override
    public String down() {
        return mCommandHandler.down(this);
    }

    @Override
    public String upMax() {
        return mCommandHandler.upMax(this);
    }

    @Override
    public String startUp() {
        return mCommandHandler.startUp(this);
    }

    @Override
    public String startDown() {
        return mCommandHandler.startDown(this);
    }

    @Override
    public String stop() {
        return mCommandHandler.stop(this);
    }

    @Override
    public String update() {
        return mCommandHandler.update(this);
    }

    @Override
    public String exact(String level) {
        return mCommandHandler.exact(this, level);
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.fh_zwickau.informatik.sensor.model.devices.Device#toString()
     */
    @Override
    public String toString() {
        return "SwitchMultilevelBlinds [ " + super.toString() + " ]";
    }
}
