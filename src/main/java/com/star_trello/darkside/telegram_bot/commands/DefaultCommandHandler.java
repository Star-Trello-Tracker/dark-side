package com.star_trello.darkside.telegram_bot.commands;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class DefaultCommandHandler implements CommandHandler {
    @Override
    public SendMessage handleMessage(Message message) {
        SendMessage response = new SendMessage();
        response.setChatId(message.getChatId());
        response.setText("Я не знаю такую команду.");
        // todo some logic for this))))
        return response;
    }

    @Override
    public CommandType getMessageType() {
        return CommandType.DEFAULT;
    }
}
