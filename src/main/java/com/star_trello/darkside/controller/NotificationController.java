package com.star_trello.darkside.controller;

import com.star_trello.darkside.model.User;
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
    public ResponseEntity<?> getAllNotificationsForUser(@RequestAttribute("user") User user) {
        return notificationService.getNotificationsForUser(user);
    }

    @PostMapping("/delete/{notificationId}")
    public ResponseEntity<?> deleteNotification(@RequestAttribute("user") User user,
                                                @PathVariable int notificationId) {
        return notificationService.deleteNotification(user, notificationId);
    }
}
