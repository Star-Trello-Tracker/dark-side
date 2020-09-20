package com.star_trello.darkside.telegram_bot.commands;

import com.star_trello.darkside.telegram_bot.services.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class UnsubscribeCommandHandler implements CommandHandler {
    @Autowired
    SubscribeService subscribeService;
    @Override
    public SendMessage handleMessage(Message message) {
        SendMessage response = new SendMessage();
        response.setChatId(message.getChatId());
        response.setText("Вы отписались от уведомлений.");
        subscribeService.unsubscribeUser(message.getFrom().getUserName(), message.getChatId());
        return response;
    }

    @Override
    public CommandType getMessageType() {
        return CommandType.UNSUBSCRIBE_ME;
    }
}
