/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor.model.profiles;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

/**
 * The {@link ProfileListDeserializer} build a profile list based on JSON data. See
 * also: http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class ProfileListDeserializer {
    /**
     * @param profilesAsJson profile list as JSON data
     * @return profile list
     */
    public ProfileList deserializeProfileList(JsonArray profilesAsJson) {
        ProfileList profileList = new ProfileList();

        for (JsonElement profileAsJson : profilesAsJson) {
            Profile profile = deserializeProfile(profileAsJson);

            if (profile != null) {
                profileList.addProfile(profile);
            }
        }

        return profileList;
    }

    /**
     * @param profileAsJson a single profile as JSON data
     * @return profile
     */
    public Profile deserializeProfile(JsonElement profileAsJson) {
        Gson gson = new Gson();

        Profile profile = gson.fromJson(profileAsJson, Profile.class);

        return profile;
    }
}
