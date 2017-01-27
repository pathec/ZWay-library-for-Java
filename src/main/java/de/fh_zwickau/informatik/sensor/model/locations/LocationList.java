/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor.model.locations;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * The {@link LocationList} represents a location list. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class LocationList {
    private List<Location> mLocations;

    /**
     * Instantiate a location list with default values.
     */
    public LocationList() {
        mLocations = new ArrayList<Location>();
    }

    public void addLocation(Location location) {
        mLocations.add(location);
    }

    public List<Location> getLocations() {
        return mLocations;
    }

    /**
     * Return the first occurrence of location
     *
     * @param id ZAutomation location id
     * @return location or null if not found
     */
    public Location getLocationById(Integer id) {
        for (Location location : mLocations) {
            if (location.getId().equals(id)) {
                return location;
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this);
        for (Location location : mLocations) {
            toStringBuilder.append(location);
        }

        return new ToStringBuilder(this).append("locationList", toStringBuilder.toString()).toString();
    }
}
