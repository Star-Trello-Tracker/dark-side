package com.star_trello.darkside.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditingProfileDto {
    private String name;

    private String surname;

    private String tgUsername;
}
