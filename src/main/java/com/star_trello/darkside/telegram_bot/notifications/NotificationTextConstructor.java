package com.star_trello.darkside.telegram_bot.notifications;

import com.star_trello.darkside.model.Notification;
import com.star_trello.darkside.model.NotificationType;

public interface NotificationTextConstructor {
    String getNotificationText(Notification notification, String commentText);
    NotificationType getNotificationType();

}
