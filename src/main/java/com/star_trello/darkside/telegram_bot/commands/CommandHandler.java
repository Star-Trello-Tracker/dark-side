package com.star_trello.darkside.telegram_bot.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface CommandHandler {
    SendMessage handleMessage(Message message);
    CommandType getMessageType();
}
