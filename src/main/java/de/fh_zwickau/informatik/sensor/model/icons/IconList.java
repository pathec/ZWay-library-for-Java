/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor.model.icons;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * The {@link IconList} represents a icon list. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class IconList {
    private List<Icon> mIcons;

    /**
     * Instantiate a icon list with default values.
     */
    public IconList() {
        mIcons = new ArrayList<Icon>();
    }

    public void addIcon(Icon icon) {
        mIcons.add(icon);
    }

    public List<Icon> getIcons() {
        return mIcons;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this);
        for (Icon icon : mIcons) {
            toStringBuilder.append(icon);
        }

        return new ToStringBuilder(this).append("iconList", toStringBuilder.toString()).toString();
    }
}
