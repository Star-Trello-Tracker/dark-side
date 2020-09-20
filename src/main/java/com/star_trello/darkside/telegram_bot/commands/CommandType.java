package com.star_trello.darkside.telegram_bot.commands;

public enum CommandType {
    GET_MY_TASKS_AS_OBSERVER("/observer"),
    GET_MY_TASKS_AS_CREATOR("/creator"),
    GET_MY_TASKS_AS_ASSIGNEE("/assignee"),
    SUBSCRIBE_ME("/subscribe"),
    UNSUBSCRIBE_ME("/unsubscribe"),
    DEFAULT(""),
    USER_NOT_FOUND(""),
    START("/start");

    private String command;

    CommandType(String command) {
        this.command = command;
    }

    public static CommandType getMessageTypeByText(String messageText) {
        for (CommandType type : values()) {
            if (type.command.equals(messageText.toLowerCase())) {
                return type;
            }
        }
        return CommandType.DEFAULT;
    }

}
