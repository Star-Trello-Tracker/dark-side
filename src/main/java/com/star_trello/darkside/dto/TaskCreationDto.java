package com.star_trello.darkside.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TaskCreationDto {
    private String title;
    private String description;
    private int priorityCode;
    private String queueTitle;
}
