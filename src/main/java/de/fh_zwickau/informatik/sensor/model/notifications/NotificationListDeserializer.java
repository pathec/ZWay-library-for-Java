/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor.model.notifications;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

/**
 * The {@link NotificationListDeserializer} build a notification list based on JSON data. See
 * also: http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class NotificationListDeserializer {
    /**
     * @param notificationsAsJson notification list as JSON data
     * @return notification list
     */
    public NotificationList deserializeNotificationList(JsonArray notificationsAsJson) {
        NotificationList notificationList = new NotificationList();

        for (JsonElement notificationAsJson : notificationsAsJson) {
            Notification notification = deserializeNotification(notificationAsJson);

            if (notification != null) {
                notificationList.addNotification(notification);
            }
        }

        return notificationList;
    }

    /**
     * @param notificationAsJson a single notification as JSON data
     * @return notification
     */
    public Notification deserializeNotification(JsonElement notificationAsJson) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();

        Notification notification = gson.fromJson(notificationAsJson, Notification.class);

        return notification;
    }
}
