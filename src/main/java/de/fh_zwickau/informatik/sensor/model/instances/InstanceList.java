/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor.model.instances;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * The {@link InstanceList} represents a instance list. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class InstanceList {
    private List<Instance> mInstances;

    /**
     * Instantiate a instance list with default values.
     */
    public InstanceList() {
        mInstances = new ArrayList<Instance>();
    }

    public void addInstance(Instance instance) {
        mInstances.add(instance);
    }

    public List<Instance> getInstances() {
        return mInstances;
    }

    /**
     * Return the first occurrence of instance
     *
     * @param moduleId ZAutomation module id
     * @return instance or null if not found
     */
    public Instance getInstanceByModuleId(String moduleId) {
        for (Instance instance : mInstances) {
            if (instance.getModuleId().equals(moduleId)) {
                return instance;
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
        for (Instance instance : mInstances) {
            toStringBuilder.append(instance);
        }

        return new ToStringBuilder(this).append("instanceList", toStringBuilder.toString()).toString();
    }
}
