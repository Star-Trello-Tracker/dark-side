package com.star_trello.darkside.telegram_bot.notifications;

import com.star_trello.darkside.model.Notification;
import com.star_trello.darkside.model.NotificationType;
import com.star_trello.darkside.telegram_bot.utils.TextUtils;
import org.springframework.stereotype.Component;

@Component
public class NewCommentInTaskNotificationTextConstructor implements NotificationTextConstructor {
    @Override
    public String getNotificationText(Notification notification, String commentText) {
        return "Пользователь " + TextUtils.fullUsername(notification.getInitiator()) +
                " оставил новый комментарий к задаче " +
                TextUtils.fullTaskTitle(notification.getTask())
                + "\nТекст комментария: " + commentText;
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.ADDED_COMMENT_IN_TASK;
    }
}
