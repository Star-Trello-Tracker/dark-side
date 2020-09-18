package com.star_trello.darkside.service;

import com.star_trello.darkside.dto.TaskCreationDto;
import com.star_trello.darkside.model.*;
import com.star_trello.darkside.repo.QueueRepo;
import com.star_trello.darkside.repo.TaskRepo;
import com.star_trello.darkside.repo.UserSessionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class TaskService {
    @Autowired
    TaskRepo taskRepo;

    @Autowired
    QueueRepo queueRepo;

    @Autowired
    UserSessionService userSessionService;

    @Autowired
    UserSessionRepo userSessionRepo;

    @Autowired
    NotificationService notificationService;

    @Transactional
    public ResponseEntity<?> createTask(String token, TaskCreationDto request) {
        User creator = userSessionService.getUserByToken(token);
        if (creator == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is invalid");
        }
        if (!queueRepo.existsByTitle(request.getQueueTitle())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(request.getQueueTitle() + " queue doesn't exist.");
        }
        Queue queue = queueRepo.getByTitle(request.getQueueTitle());
        String key = queue.getTitle() + "-" + (queue.getTaskList().size() + 1);
        Task task = Task.builder()
                .creator(creator)
                .title(request.getTitle())
                .description(request.getDescription())
                .key(key)
                .priority(TaskPriority.findPriorityByCode(request.getPriorityCode()))
                .status(TaskStatus.OPEN)
                .comments(new ArrayList<>())
                .observers(new ArrayList<>())
                .calledUsers(new ArrayList<>())
                .refreshed(System.currentTimeMillis())
                .build();
        queue.getTaskList().add(task);
        taskRepo.save(task);
        queueRepo.save(queue);
        return ResponseEntity.ok().body(task);
    }

    public ResponseEntity<?> getTaskById(String token, int taskId) {
        if (!userSessionRepo.existsByToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is invalid");
        }
        if (!taskRepo.existsById(taskId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task with id " + taskId + " doesn't exist.");
        }
        return ResponseEntity.ok().body(taskRepo.findById(taskId));
    }

    @Transactional
    public ResponseEntity<?> changeTaskPriority(String token, int taskId, int priorityCode) {
        User initiator = userSessionService.getUserByToken(token);
        if (initiator == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is invalid");
        }
        if (!taskRepo.existsById(taskId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task with id " + taskId + " doesn't exist.");
        }
        Task task = taskRepo.getById(taskId);
        if (!task.getCreator().equals(initiator)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Task update is forbidden for this user.");
        }
        task.setPriority(TaskPriority.findPriorityByCode(priorityCode));
        task.setRefreshed(System.currentTimeMillis());
        notificationService.createNotification(task, task.getObservers(), initiator, NotificationType.TASK_PRIORITY_UPDATED);
        taskRepo.save(task);
        return ResponseEntity.ok("Priority changed successfully");
    }

    @Transactional
    public ResponseEntity<?> changeTaskStatus(String token, int taskId, int statusCode) {
        User initiator = userSessionService.getUserByToken(token);
        if (initiator == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is invalid");
        }
        if (!taskRepo.existsById(taskId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task with id " + taskId + " doesn't exist.");
        }
        Task task = taskRepo.getById(taskId);
        if (!task.getCreator().equals(initiator)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Task update is forbidden for this user.");
        }
        task.setStatus(TaskStatus.findStatusByCode(statusCode));
        task.setRefreshed(System.currentTimeMillis());
        notificationService.createNotification(task, task.getObservers(), initiator, NotificationType.TASK_STATUS_UPDATED);
        taskRepo.save(task);
        return ResponseEntity.ok("Status changed successfully");
    }

    @Transactional
    public ResponseEntity<?> changeTaskDescription(String token, int taskId, String description) {
        User initiator = userSessionService.getUserByToken(token);
        if (initiator == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is invalid");
        }
        if (!taskRepo.existsById(taskId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task with id " + taskId + " doesn't exist.");
        }
        Task task = taskRepo.getById(taskId);
        if (!task.getCreator().equals(initiator)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Task update is forbidden for this user.");
        }
        task.setDescription(description);
        task.setRefreshed(System.currentTimeMillis());
        notificationService.createNotification(task, task.getObservers(), initiator, NotificationType.TASK_DESCRIPTION_UPDATED);
        taskRepo.save(task);
        return ResponseEntity.ok("Description changed successfully");
    }

    @Transactional
    public ResponseEntity<?> assignUser(String token, int taskId, String username) {
        User initiator = userSessionService.getUserByToken(token);
        if (initiator == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is invalid");
        }
        User assignee = userSessionService.getUserByToken(token);
        if (assignee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with username " + username + " doesn't exist.");
        }
        if (!taskRepo.existsById(taskId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task with id " + taskId + " doesn't exist.");
        }
        Task task = taskRepo.getById(taskId);
        if (!(task.getCreator().equals(initiator)||initiator.equals(assignee))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Assign is forbidden for this user.");
        }
        task.setAssignee(assignee);
        task.getObservers().add(assignee);
        task.setRefreshed(System.currentTimeMillis());
        notificationService.createNotification(task, Arrays.asList(assignee), initiator, NotificationType.ASSIGNED_TO_TASK);
        taskRepo.save(task);
        return ResponseEntity.ok("Assigned to task successfully");
    }
}
