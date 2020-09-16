package com.star_trello.darkside.controller;

import com.star_trello.darkside.dto.UserCredentialsDto;
import com.star_trello.darkside.model.User;
import com.star_trello.darkside.model.UserSession;
import com.star_trello.darkside.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserCredentialsDto userCredentials) {
        return authService.login(userCredentials);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        return authService.register(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody UserSession userSession) {
        return authService.logout(userSession);
    }
}
