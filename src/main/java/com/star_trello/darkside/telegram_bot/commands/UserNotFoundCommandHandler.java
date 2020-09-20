package com.star_trello.darkside.telegram_bot.commands;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class UserNotFoundCommandHandler implements CommandHandler {
    @Override
    public SendMessage handleMessage(Message message) {
        SendMessage response = new SendMessage();
        response.setChatId(message.getChatId());
        response.setText("Не могу найти Ваш аккаунт в StarTrelloTracker. " +
                "Если Вы зарегистрированы, проверьте, заполнено ли поле Telegram в профиле.");
        return response;
    }

    @Override
    public CommandType getMessageType() {
        return CommandType.USER_NOT_FOUND;
    }
}
