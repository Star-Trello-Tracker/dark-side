package com.star_trello.darkside.telegram_bot.services;

import com.star_trello.darkside.telegram_bot.commands.CommandHandler;
import com.star_trello.darkside.telegram_bot.commands.CommandType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageService {
    @Autowired
    TrackerService trackerService;

    private final Map<CommandType, CommandHandler> messageHandlerMap = new HashMap<>();

    public MessageService(@Autowired List<CommandHandler> handlers) {
        for (CommandHandler handler : handlers) {
            messageHandlerMap.put(handler.getMessageType(), handler);
        }
    }

    public SendMessage getResponse(Update update) {
        Message message = update.getMessage();
        String userName = message.getFrom().getUserName();
        CommandType type;
        if (!trackerService.userExistsByTgUsername(userName)) {
            type = CommandType.USER_NOT_FOUND;
        }
        else {
            type = CommandType.getMessageTypeByText(message.getText());
        }
        return messageHandlerMap.get(type).handleMessage(message);
    }

}
