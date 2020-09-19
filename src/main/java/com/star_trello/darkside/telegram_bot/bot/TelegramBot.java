package com.star_trello.darkside.telegram_bot.bot;

import com.star_trello.darkside.model.Notification;
import com.star_trello.darkside.telegram_bot.services.MessageService;
import com.star_trello.darkside.telegram_bot.services.TelegramNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    private static final Logger logger = LoggerFactory.getLogger(TelegramBot.class);

    @Autowired
    MessageService messageService;
    @Autowired
    TelegramNotificationService notificationService;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            SendMessage response = messageService.getResponse(update);
            try {
                execute(response);
                logger.info("Sent message \"{}\" to {}", response.getText(), update.getMessage().getFrom().getUserName());
            } catch (TelegramApiException e) {
                logger.error("Failed to send message due to error: {}", e.getMessage());
            }
        }
    }

    @Override
    public String getBotUsername() {
        return System.getenv("USERNAME");
    }

    @Override
    public String getBotToken() {
        return System.getenv("TOKEN");
    }

    public void sendNotification(Notification notification, String commentText) {
        logger.info("checking subscription");
        if (!notificationService.isUserSubscribed(notification)) return;
        try {
            logger.info("User is subscribed. Trying to send..");
            execute(notificationService.getNotificationMessage(notification, commentText));
        } catch (TelegramApiException e) {
            logger.error("Failed to send message due to error: {}", e.getMessage());
        }
    }

}
