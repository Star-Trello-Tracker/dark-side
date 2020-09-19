package com.star_trello.darkside.service;

import com.star_trello.darkside.dto.TaskCreationDto;
import com.star_trello.darkside.model.Queue;
import com.star_trello.darkside.model.*;
import com.star_trello.darkside.repo.QueueRepo;
import com.star_trello.darkside.repo.TaskRepo;
import com.star_trello.darkside.repo.UserSessionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TaskService {
    @Autowired
    TaskRepo taskRepo;

    @Autowired
    QueueRepo queueRepo;

    @Autowired
    UserSessionService userSessionService;

    @Autowired
    UserService userService;

    @Autowired
    UserSessionRepo userSessionRepo;

    @Autowired
    NotificationService notificationService;

    @Transactional
    public ResponseEntity<?> getAllTasks() {
        return ResponseEntity.ok(taskRepo.findAll());
    }

    @Transactional
    public ResponseEntity<?> createTask(User creator, TaskCreationDto request) {
        List<String> observers = request.getObservers();
        observers.add(creator.getUsername());

        if (!queueRepo.existsByTitle(request.getQueueTitle())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(request.getQueueTitle() + " queue doesn't exist.");
        }
        User assignee = userService.getUserByUsername(request.getAssignee());

        if (assignee != null) {
            observers.add(assignee.getUsername());
        }

        Set<User> observersList = getObserversList(observers);
        Queue queue = queueRepo.getByTitle(request.getQueueTitle());
        String key = queue.getTitle() + "-" + (queue.getTaskList().size() + 1);
        Task task = Task.builder()
                .creator(creator)
                .assignee(assignee)
                .observers(observersList)
                .title(request.getTitle())
                .description(request.getDescription())
                .key(key)
                .priority(TaskPriority.findPriorityByCode(request.getPriorityCode()))
                .status(TaskStatus.OPEN)
                .comments(new ArrayList<>())
                .calledUsers(new HashSet<>())
                .refreshed(System.currentTimeMillis())
                .build();

        queue.getTaskList().add(task);
        taskRepo.save(task);
        queueRepo.save(queue);
        return ResponseEntity.ok().body(task);
    }

    @Transactional
    public Set<User> getObserversList(List<String> usernames) {
        Set<User> set = new HashSet<>();
        for (String username : usernames) {
            User observer = userService.getUserByUsername(username);
            if (observer != null) {
                set.add(observer);
            }
        }

        return set;
    }

    @Transactional
    public ResponseEntity<?> getTaskByKey(String key) {
        Task task = taskRepo.getByKey(key);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task with id " + key + " doesn't exist.");
        }
        return ResponseEntity.ok().body(task);
    }

    @Transactional
    public ResponseEntity<?> changeTaskPriority(User initiator, int taskId, int priorityCode) {
        if (!taskRepo.existsById(taskId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task with id " + taskId + " doesn't exist.");
        }
        Task task = taskRepo.getById(taskId);
        if (!task.getCreator().equals(initiator) && !initiator.equals(task.getAssignee())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Task update is forbidden for this user.");
        }
        task.setPriority(TaskPriority.findPriorityByCode(priorityCode));
        task.setRefreshed(System.currentTimeMillis());
        notificationService.createNotification(task, task.getObservers(), initiator, NotificationType.TASK_PRIORITY_UPDATED);
        taskRepo.save(task);
        return ResponseEntity.ok().build();
    }

    @Transactional
    public ResponseEntity<?> changeTaskStatus(User initiator, int taskId, int statusCode) {
        if (!taskRepo.existsById(taskId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task with id " + taskId + " doesn't exist.");
        }
        Task task = taskRepo.getById(taskId);
        if (!task.getCreator().equals(initiator) && !initiator.equals(task.getAssignee())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Task update is forbidden for this user.");
        }
        task.setStatus(TaskStatus.findStatusByCode(statusCode));
        task.setRefreshed(System.currentTimeMillis());
        notificationService.createNotification(task, task.getObservers(), initiator, NotificationType.TASK_STATUS_UPDATED);
        taskRepo.save(task);
        return ResponseEntity.ok().build();
    }

    @Transactional
    public ResponseEntity<?> changeTaskTitle(User initiator, int taskId, String title) {
        if (initiator == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is invalid");
        }
        if (!taskRepo.existsById(taskId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task with id " + taskId + " doesn't exist.");
        }
        Task task = taskRepo.getById(taskId);
        if (!task.getCreator().equals(initiator) && !initiator.equals(task.getAssignee())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Task update is forbidden for this user.");
        }
        task.setTitle(title);
        task.setRefreshed(System.currentTimeMillis());
        notificationService.createNotification(task, task.getObservers(), initiator, NotificationType.TASK_TITLE_UPDATED);
        taskRepo.save(task);
        return ResponseEntity.ok().build();
    }

    @Transactional
    public ResponseEntity<?> changeTaskDescription(User initiator, int taskId, String description) {
        if (!taskRepo.existsById(taskId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task with id " + taskId + " doesn't exist.");
        }
        Task task = taskRepo.getById(taskId);
        if (!task.getCreator().equals(initiator) && !initiator.equals(task.getAssignee())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Task update is forbidden for this user.");
        }
        task.setDescription(description);
        task.setRefreshed(System.currentTimeMillis());
        notificationService.createNotification(task, task.getObservers(), initiator, NotificationType.TASK_DESCRIPTION_UPDATED);
        taskRepo.save(task);
        return ResponseEntity.ok().build();
    }

    @Transactional
    public ResponseEntity<?> assignUser(User initiator, int taskId, String username) {
        if (!taskRepo.existsById(taskId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task with id " + taskId + " doesn't exist.");
        }

        Task task = taskRepo.getById(taskId);

        User assignee = userService.getUserByUsername(username);
        if (assignee == null) {
            task.setAssignee(null);
            return ResponseEntity.ok().build();
        }
        if (!(task.getCreator().equals(initiator)||initiator.equals(assignee))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Assign is forbidden for this user.");
        }
        task.setAssignee(assignee);
        task.getObservers().add(assignee);
        task.setRefreshed(System.currentTimeMillis());
        notificationService.createNotification(
                task,
                new HashSet<>(Collections.singletonList(assignee)),
                initiator,
                NotificationType.ASSIGNED_TO_TASK
        );
        taskRepo.save(task);
        return ResponseEntity.ok().build();
    }

    @Transactional
    public ResponseEntity<?> setObserver(User initiator, int taskId) {
        if (!taskRepo.existsById(taskId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task with id " + taskId + " doesn't exist.");
        }

        Task task = taskRepo.getById(taskId);

        task.getObservers().add(initiator);

        taskRepo.save(task);

        return ResponseEntity.ok().build();
    }
}
