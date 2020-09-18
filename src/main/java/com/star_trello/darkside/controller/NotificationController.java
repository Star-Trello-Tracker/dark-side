package com.star_trello.darkside.controller;

import com.star_trello.darkside.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    @GetMapping("")
    public ResponseEntity<?> getAllNotificationsForUser(@RequestHeader("Authorization") String token) {
        return notificationService.getNotificationsForUser(token);
    }

    @PostMapping("/delete/{notificationId}")
    public ResponseEntity<?> deleteNotification(@RequestHeader("Authorization") String token,
                                                @PathVariable int notificationId) {
        return notificationService.deleteNotification(token, notificationId);
    }
}
