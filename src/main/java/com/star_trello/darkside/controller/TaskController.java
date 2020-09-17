package com.star_trello.darkside.controller;

import com.star_trello.darkside.dto.TaskCreationDto;
import com.star_trello.darkside.model.TaskPriority;
import com.star_trello.darkside.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    TaskService taskService;

    @PostMapping("")
    public ResponseEntity<?> createTask(@RequestBody TaskCreationDto request, @RequestHeader("Authorization") String token) {
        return taskService.createTask(token, request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable int id, @RequestHeader("Authorization") String token) {
        return taskService.getTaskById(token, id);
    }

    @PostMapping("/{taskId}/priority/change")
    public ResponseEntity<?> changeTaskPriority(@PathVariable int taskId,
                                                @RequestBody TaskPriority priorityCode,
                                                @RequestHeader("Authorization") String token) {
        return taskService.changeTaskPriority(token, taskId, priorityCode);
    }

    @PostMapping("/{taskId}/status/change")
    public ResponseEntity<?> changeTaskStatus(@PathVariable int taskId,
                                                @RequestBody int statusCode,
                                                @RequestHeader("Authorization") String token) {
        return taskService.changeTaskStatus(token, taskId, statusCode);
    }

    @PostMapping("/{taskId}/description/change")
    public ResponseEntity<?> changeTaskDescription(@PathVariable int taskId,
                                              @RequestBody String description,
                                              @RequestHeader("Authorization") String token) {
        return taskService.changeTaskDescription(token, taskId, description);
    }
}
