package com.star_trello.darkside.telegram_bot.utils;

import com.star_trello.darkside.model.Task;
import com.star_trello.darkside.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TextUtils {
    public static String getTaskTextDescription(Task task) {
        return "------------------\n" + fullTaskTitle(task) + "\n" +
                "Создатель: " + fullUsername(task.getCreator()) + "\n" +
                "Исполнитель: " + fullUsername(task.getAssignee()) + "\n" +
                "Приоритет: " + task.getPriority() + "\n" +
                "Статус: " + task.getStatus() + "\n" +
                "Описание: " + task.getDescription() + "\n" + "------------------\n";
    }

    public static String getTaskListAsText(List<Task> tasks) {
        if (tasks.isEmpty()) {
            return "Таких задач нет.";
        }
        StringBuilder result = new StringBuilder();
        for (Task task : tasks) {
            result.append(TextUtils.getTaskTextDescription(task));
        }
        return result.toString();
    }

    public static String fullUsername(User user) {
        if (user == null) return "Нет";
        StringBuilder result = new StringBuilder(user.getUsername());
        result.append((user.getSurname() == null) ? "" : " " + user.getSurname());
        result.append((user.getName() == null) ? "" : " " + user.getName());
        return result.toString();
    }
    public static String fullTaskTitle(Task task) {
        return task.getKey() + ": " + task.getTitle();
    }

    public static String hideMentionsFromText(String commentText) {
        return commentText.replace("@", "");
    }
}
