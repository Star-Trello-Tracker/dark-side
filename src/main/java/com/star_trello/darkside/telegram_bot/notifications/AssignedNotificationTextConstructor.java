package com.star_trello.darkside.telegram_bot.notifications;

import com.star_trello.darkside.model.Notification;
import com.star_trello.darkside.model.NotificationType;
import com.star_trello.darkside.telegram_bot.utils.TextUtils;
import org.springframework.stereotype.Component;

@Component
public class AssignedNotificationTextConstructor implements NotificationTextConstructor {
    @Override
    public String getNotificationText(Notification notification, String commentText) {
        return "Вы назначены пользователем " +
                TextUtils.fullUsername(notification.getInitiator()) +
                " исполнителем на задачу " + notification.getTask().getKey() +
                ": " + notification.getTask().getTitle();
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.ASSIGNED_TO_TASK;
    }
}
