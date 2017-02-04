/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor.model.instances;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import de.fh_zwickau.informatik.sensor.model.instances.dummydevice.DummyDevice;
import de.fh_zwickau.informatik.sensor.model.instances.openhabconnector.OpenHABConnector;

/**
 * The {@link InstanceListDeserializer} build a instance list with known, concrete instances based on JSON data. See
 * also: http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class InstanceListDeserializer {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @param instancesAsJson instance list as JSON data
     * @return instance list
     */
    public InstanceList deserializeInstanceList(JsonArray instancesAsJson) {
        InstanceList instanceList = new InstanceList();

        for (JsonElement instanceAsJson : instancesAsJson) {
            Instance instance = deserializeInstance(instanceAsJson);

            if (instance != null) {
                instanceList.addInstance(instance);
            }
        }

        return instanceList;
    }

    /**
     * @param instanceAsJson a single instance as JSON data
     * @return instance
     */
    public Instance deserializeInstance(JsonElement instanceAsJson) {
        Gson gson = new Gson();

        String moduleId = instanceAsJson.getAsJsonObject().get("moduleId").getAsString();
        Instance instance = null;
        switch (moduleId) {
            case "OpenHABConnector":
                instance = gson.fromJson(instanceAsJson, OpenHABConnector.class);
                break;
            case "DummyDevice":
                instance = gson.fromJson(instanceAsJson, DummyDevice.class);
                break;
            default:
                logger.debug("Unknown module id: " + moduleId);
                break;
        }
        return instance;
    }
}
