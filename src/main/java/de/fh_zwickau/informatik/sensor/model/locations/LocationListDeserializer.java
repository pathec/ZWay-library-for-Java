/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor.model.locations;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

/**
 * The {@link LocationListDeserializer} build a location list based on JSON data. See
 * also: http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class LocationListDeserializer {
    /**
     * @param locationsAsJson location list as JSON data
     * @return location list
     */
    public LocationList deserializeLocationList(JsonArray locationsAsJson) {
        LocationList locationList = new LocationList();

        for (JsonElement locationAsJson : locationsAsJson) {
            Location location = deserializeLocation(locationAsJson);

            if (location != null) {
                locationList.addLocation(location);
            }
        }

        return locationList;
    }

    /**
     * @param locationAsJson a single location as JSON data
     * @return location
     */
    public Location deserializeLocation(JsonElement locationAsJson) {
        Gson gson = new Gson();

        Location location = gson.fromJson(locationAsJson, Location.class);

        return location;
    }
}
