package com.star_trello.darkside.service;

import com.star_trello.darkside.model.NotificationType;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class AutoCommentsService {
    private final String[] priorities = new String[]{
            "Минор",
            "Средний",
            "Высокий",
            "Блокер",
            "Баг",
            "Критичный баг",
    };

    Map<NotificationType, List<String>> autoCommentsTextMap = Stream.of(new Object[][]{
            { NotificationType.TASK_PRIORITY_UPDATED, Arrays.asList(
                    "изменил приоритет задачи на "
            ) },
            { NotificationType.TASK_STATUS_UPDATED, Arrays.asList(
                    "открыл задачу",
                    "перевел в состояние 'В работе'",
                    "перевел в состояние 'Нужна информация'",
                    "перевел в состояние 'В ревью'",
                    "перевел в состояние 'В тестировании'",
                    "перевел в состояние 'Решена'",
                    "закрыл задачу"
            ) },
            { NotificationType.TASK_DESCRIPTION_UPDATED, Collections.singletonList("изменил описание задачи")},
            { NotificationType.TASK_TITLE_UPDATED, Collections.singletonList("изменил название задачи на") }
    }).collect(Collectors
            .toMap(data -> (NotificationType) data[0], data -> (List<String>) data[1])
    );

    public String getAutoCommentText(Map<NotificationType, String> type) {
        NotificationType typeMessage = (NotificationType) type.keySet().toArray()[0];
        List<String> messages = this.autoCommentsTextMap.get(typeMessage);

        String value = type.get(typeMessage);

        if (typeMessage.equals(NotificationType.TASK_STATUS_UPDATED)) {
            return messages.get(Integer.parseInt(value) - 1);
        }

        if (typeMessage.equals(NotificationType.TASK_PRIORITY_UPDATED)) {
            return messages.get(0) + this.priorities[Integer.parseInt(value) - 1];
        }

        if (typeMessage.equals(NotificationType.TASK_TITLE_UPDATED)) {
            return messages.get(0) + " \"" + value + "";
        }

        return messages.get(0);
    }
}
