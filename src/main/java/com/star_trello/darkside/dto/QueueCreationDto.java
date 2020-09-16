package com.star_trello.darkside.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class QueueCreationDto {
    private String title;
    private String description;
}
