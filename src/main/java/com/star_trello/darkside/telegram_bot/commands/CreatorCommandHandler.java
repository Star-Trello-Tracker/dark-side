package com.star_trello.darkside.telegram_bot.commands;

import com.star_trello.darkside.model.Task;
import com.star_trello.darkside.telegram_bot.services.TrackerService;
import com.star_trello.darkside.telegram_bot.utils.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Component
public class CreatorCommandHandler implements CommandHandler {
    @Autowired
    TrackerService trackerService;

    @Override
    public SendMessage handleMessage(Message message) {
        SendMessage response = new SendMessage();
        Long chatId = message.getChatId();
        response.setChatId(chatId);
        List<Task> tasks = trackerService.getCreatedTasksForUser(
                message.getFrom().getUserName());
        String responseText = "Задачи, в которых Вы создатель:\n" + TextUtils.getTaskListAsText(tasks);
        response.setText(responseText);
        return response;
    }

    @Override
    public CommandType getMessageType() {
        return CommandType.GET_MY_TASKS_AS_CREATOR;
    }
}
