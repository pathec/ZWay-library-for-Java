/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor.model.notifications;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import de.fh_zwickau.informatik.sensor.ZWayConstants;

/**
 * The {@link NotificationListDeserializer} build a notification list based on JSON data. See
 * also: http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class NotificationListDeserializer {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

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

        String notificationType = notificationAsJson.getAsJsonObject().get("level").getAsString();
        Notification notification = null;
        switch (notificationType) {
            case ZWayConstants.NOTIFICATION_TYPE_DEVICE_INFO:
                notification = gson.fromJson(notificationAsJson, Notification.class);
                break;
            default:
                logger.debug("Unknown notification type: " + notificationType);
        }

        return notification;
    }
}
