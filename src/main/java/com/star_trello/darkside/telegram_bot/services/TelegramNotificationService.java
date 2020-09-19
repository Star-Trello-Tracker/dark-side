package com.star_trello.darkside.telegram_bot.services;

import com.star_trello.darkside.model.Notification;
import com.star_trello.darkside.model.NotificationType;
import com.star_trello.darkside.telegram_bot.notifications.NotificationTextConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TelegramNotificationService {

    @Autowired
    SubscribeService subscribeService;

    private final Map<NotificationType, NotificationTextConstructor> textConstructorMap = new HashMap<>();

    public TelegramNotificationService(@Autowired List<NotificationTextConstructor> constructors) {
        for (NotificationTextConstructor constructor : constructors) {
            textConstructorMap.put(constructor.getNotificationType(), constructor);
        }
    }

    public SendMessage getNotificationMessage(Notification notification, String commentText) {
        SendMessage message = new SendMessage();
        message.setChatId(subscribeService.getChatIdByTgUsername(notification.getCalledUser().getTgUsername()));
        String messageText = textConstructorMap.get(notification.getType()).getNotificationText(notification, commentText);
        message.setText(messageText);
        return message;
    }
    public boolean isUserSubscribed(Notification notification) {
        return subscribeService.isUserSubscribed(notification.getCalledUser().getTgUsername());
    }
}
