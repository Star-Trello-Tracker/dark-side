package com.star_trello.darkside.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class TaskCreationDto {
    private String title;
    private String description;
    private int priorityCode;
    private String queueTitle;
    private String assignee;
    private List<String> observers;
}
