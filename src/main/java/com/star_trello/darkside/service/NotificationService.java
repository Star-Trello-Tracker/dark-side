package com.star_trello.darkside.service;

import com.star_trello.darkside.model.Notification;
import com.star_trello.darkside.model.NotificationType;
import com.star_trello.darkside.model.Task;
import com.star_trello.darkside.model.User;
import com.star_trello.darkside.repo.NotificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class NotificationService {
    @Autowired
    NotificationRepo notificationRepo;
    @Autowired
    UserSessionService userSessionService;

    @Transactional
    public void createNotification(Task task, Set<User> willBeNotified, User initiator, NotificationType type) {
        for (User user : willBeNotified) {
            Notification notification = Notification
                    .builder()
                    .calledUser(user)
                    .initiator(initiator)
                    .task(task)
                    .type(type)
                    .build();
            notificationRepo.save(notification);
        }
    }

    @Transactional
    public ResponseEntity<?> getNotificationsForUser(User creator) {
        return ResponseEntity.ok(notificationRepo.getAllByCalledUser(creator));
    }

    @Transactional
    public ResponseEntity<?> deleteNotification(User calledUser, int notificationId) {
        if (!notificationRepo.existsById(notificationId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Notification with id " + notificationId + " doesn't exist.");
        }
        Notification notification = notificationRepo.getById(notificationId);
        if (!calledUser.equals(notification.getCalledUser())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("User doesn't have rights to delete this notification.");
        }
        notificationRepo.delete(notification);
        return ResponseEntity.ok("Notification deleted successfully");
    }
}
