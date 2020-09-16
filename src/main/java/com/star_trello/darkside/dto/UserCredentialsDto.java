package com.star_trello.darkside.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User credentials to login
 *
 * Password will encode at backend
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentialsDto {
    private String email;

    private String password;
}
