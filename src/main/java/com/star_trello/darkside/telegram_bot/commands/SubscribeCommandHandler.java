package com.star_trello.darkside.telegram_bot.commands;

import com.star_trello.darkside.telegram_bot.services.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class SubscribeCommandHandler implements CommandHandler {
    @Autowired
    SubscribeService subscribeService;
    @Override
    public SendMessage handleMessage(Message message) {
        SendMessage response = new SendMessage();
        response.setChatId(message.getChatId());
        response.setText("Вы подписаны на уведомления.");
        subscribeService.subscribeUser(message.getFrom().getUserName(), message.getChatId());
        return response;
    }

    @Override
    public CommandType getMessageType() {
        return CommandType.SUBSCRIBE_ME;
    }
}
