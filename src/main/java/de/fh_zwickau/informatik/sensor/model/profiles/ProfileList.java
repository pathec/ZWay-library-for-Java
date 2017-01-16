/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor.model.profiles;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * The {@link ProfileList} represents a profile list. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class ProfileList {
    private List<Profile> mProfiles;

    /**
     * Instantiate a instance list with default values.
     */
    public ProfileList() {
        mProfiles = new ArrayList<Profile>();
    }

    public void addProfile(Profile profile) {
        mProfiles.add(profile);
    }

    public List<Profile> getProfiles() {
        return mProfiles;
    }

    /**
     * /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this);
        for (Profile profile : mProfiles) {
            toStringBuilder.append(profile);
        }

        return new ToStringBuilder(this).append("profileList", toStringBuilder.toString()).toString();
    }
}