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
 * The {@link Camera} represents a camera from ZAutomation API. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class Camera extends Device {

    @Override
    public String zoomIn() {
        if (getMetrics().getCameraHasZoomIn()) {
            return mCommandHandler.zoomIn(this);
        } else {
            throw new UnsupportedOperationException("Operation not supported by device type " + getDeviceType());
        }
    }

    @Override
    public String zoomOut() {
        if (getMetrics().getCameraHasZoomOut()) {
            return mCommandHandler.zoomOut(this);
        } else {
            throw new UnsupportedOperationException("Operation not supported by device type " + getDeviceType());
        }
    }

    @Override
    public String left() {
        if (getMetrics().getCameraHasLeft()) {
            return mCommandHandler.left(this);
        } else {
            throw new UnsupportedOperationException("Operation not supported by device type " + getDeviceType());
        }
    }

    @Override
    public String right() {
        if (getMetrics().getCameraHasRight()) {
            return mCommandHandler.right(this);
        } else {
            throw new UnsupportedOperationException("Operation not supported by device type " + getDeviceType());
        }
    }

    @Override
    public String up() {
        if (getMetrics().getCameraHasUp()) {
            return mCommandHandler.up(this);
        } else {
            throw new UnsupportedOperationException("Operation not supported by device type " + getDeviceType());
        }
    }

    @Override
    public String down() {
        if (getMetrics().getCameraHasDown()) {
            return mCommandHandler.down(this);
        } else {
            throw new UnsupportedOperationException("Operation not supported by device type " + getDeviceType());
        }
    }

    @Override
    public String open() {
        if (getMetrics().getCameraHasOpen()) {
            return mCommandHandler.open(this);
        } else {
            throw new UnsupportedOperationException("Operation not supported by device type " + getDeviceType());
        }
    }

    @Override
    public String close() {
        if (getMetrics().getCameraHasClose()) {
            return mCommandHandler.close(this);
        } else {
            throw new UnsupportedOperationException("Operation not supported by device type " + getDeviceType());
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.model.devices.Device#toString()
     */
    @Override
    public String toString() {
        return "Camera [ " + super.toString() + " ]";
    }
}
