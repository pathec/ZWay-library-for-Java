/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor.model.icons;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

/**
 * The {@link IconListDeserializer} build a icon list with known, concrete icon based on JSON data. See
 * also: http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class IconListDeserializer {
    /**
     * @param iconsAsJson icon list as JSON data
     * @return icon list
     */
    public IconList deserializeIconList(JsonArray iconsAsJson) {
        IconList iconList = new IconList();

        for (JsonElement iconAsJson : iconsAsJson) {
            Icon icon = deserializeIcon(iconAsJson);

            if (icon != null) {
                iconList.addIcon(icon);
            }
        }

        return iconList;
    }

    /**
     * @param iconAsJson a single icon as JSON data
     * @return icon
     */
    public Icon deserializeIcon(JsonElement iconAsJson) {
        Gson gson = new Gson();

        Icon icon = gson.fromJson(iconAsJson, Icon.class);

        return icon;
    }
}
