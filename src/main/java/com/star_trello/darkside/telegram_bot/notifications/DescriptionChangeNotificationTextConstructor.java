package com.star_trello.darkside.telegram_bot.notifications;

import com.star_trello.darkside.model.Notification;
import com.star_trello.darkside.model.NotificationType;
import com.star_trello.darkside.telegram_bot.utils.TextUtils;
import org.springframework.stereotype.Component;

@Component
public class DescriptionChangeNotificationTextConstructor implements NotificationTextConstructor {
    public String getNotificationText(Notification notification, String commentText) {
        return "Пользователь " + TextUtils.fullUsername(notification.getInitiator()) +
                " изменил описание задачи " +
                TextUtils.fullTaskTitle(notification.getTask())
                + " на " + notification.getTask().getDescription();
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.TASK_DESCRIPTION_UPDATED;
    }
}
