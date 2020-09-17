package com.star_trello.darkside.model;

public enum TaskStatus {
    OPEN(1),
    IN_PROGRESS(2),
    NEED_INFO(3),
    IN_REVIEW(4),
    TESTING(5),
    SOLVED(6),
    CLOSED(7);

    private int dbCode;

    TaskStatus(int dbCode) {
        this.dbCode = dbCode;
    }

    public static TaskStatus findStatusByCode(int dbCode) {
        for (TaskStatus status : values()) {
            if (status.dbCode == dbCode) {
                return status;
            }
        }
        return TaskStatus.OPEN;
    }
}
