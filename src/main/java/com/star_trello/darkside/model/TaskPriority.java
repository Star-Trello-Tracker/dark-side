package com.star_trello.darkside.model;

public enum TaskPriority {
    MINOR(1),
    NORMAL(2),
    HIGH(3),
    BLOCKER(4),
    BUG(5),
    CRITICAL_BUG(6);

    private int dbCode;

    TaskPriority(int dbCode) {
        this.dbCode = dbCode;
    }

    public static TaskPriority findPriorityByCode(int dbCode) {
        for (TaskPriority priority : values()) {
            if (priority.dbCode == dbCode) {
                return priority;
            }
        }
        return TaskPriority.MINOR;
    }
}
