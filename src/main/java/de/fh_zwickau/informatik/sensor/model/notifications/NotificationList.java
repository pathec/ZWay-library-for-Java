/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor.model.notifications;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * The {@link NotificationList} represents a notification list. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class NotificationList {
    private List<Notification> mNotifications;

    /**
     * Instantiate a instance list with default values.
     */
    public NotificationList() {
        mNotifications = new ArrayList<Notification>();
    }

    public void addNotification(Notification notification) {
        mNotifications.add(notification);
    }

    public List<Notification> getNotifications() {
        return mNotifications;
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
        for (Notification notification : mNotifications) {
            toStringBuilder.append(notification);
        }

        return new ToStringBuilder(this).append("notificationList", toStringBuilder.toString()).toString();
    }
}
