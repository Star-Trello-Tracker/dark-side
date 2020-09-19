package com.star_trello.darkside.telegram_bot.notifications;

import com.star_trello.darkside.model.Notification;
import com.star_trello.darkside.model.NotificationType;
import com.star_trello.darkside.telegram_bot.utils.TextUtils;
import org.springframework.stereotype.Component;

@Component
public class StatusChangeNotificationTextConstructor implements NotificationTextConstructor {
    @Override
    public String getNotificationText(Notification notification, String commentText) {
        return "Пользователь " + TextUtils.fullUsername(notification.getInitiator()) +
                " изменил статус задачи " +
                TextUtils.fullTaskTitle(notification.getTask())
                + " на " + notification.getTask().getStatus();
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.TASK_STATUS_UPDATED;
    }
}
