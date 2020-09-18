package com.star_trello.darkside.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class CommentCreationDto {
    private String taskKey;
    private String comment;
    private List<String> whoCalled;
}
