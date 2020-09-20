package com.star_trello.darkside.telegram_bot.commands;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class StartCommandHandler implements CommandHandler {

    @Override
    public SendMessage handleMessage(Message message) {
        SendMessage response = new SendMessage();
        response.setChatId(message.getChatId());
        response.setText("Привет!\n Наберите / ,чтобы узнать список доступных команд");
        return response;
    }

    @Override
    public CommandType getMessageType() {
        return CommandType.START;
    }
}
