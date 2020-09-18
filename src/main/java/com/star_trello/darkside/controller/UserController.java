package com.star_trello.darkside.controller;

import com.star_trello.darkside.dto.EditingProfileDto;
import com.star_trello.darkside.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserData(@PathVariable Integer id) {
        return userService.getUserByIdWrapper(id);
    }

    @PostMapping("/edit")
    public ResponseEntity<?> updateUserProfile(@RequestHeader("Authorization") String token,
                                               @RequestBody EditingProfileDto editingProfile) {
        return userService.updateUserProfile(token, editingProfile);
    }

    @GetMapping("/usernames")
    public ResponseEntity<?> getAllUserNames() {
        return userService.getAllUsernames();
    }

    @GetMapping("/fullnames")
    public ResponseEntity<?> getAllNamesAndUsernames() { return userService.getAllNamesAndUsernames(); }
}
