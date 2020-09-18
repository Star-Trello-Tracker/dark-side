package com.star_trello.darkside.controller;

import com.star_trello.darkside.dto.CodeDto;
import com.star_trello.darkside.dto.TaskCreationDto;
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

    @GetMapping("/{key}")
    public ResponseEntity<?> getTaskById(@PathVariable String key, @RequestHeader("Authorization") String token) {
        return taskService.getTaskById(token, key);
    }

    @PostMapping("/{taskId}/priority/change")
    public ResponseEntity<?> changeTaskPriority(@PathVariable int taskId,
                                                @RequestBody CodeDto priorityCode,
                                                @RequestHeader("Authorization") String token) {
        return taskService.changeTaskPriority(token, taskId, priorityCode.getCode());
    }

    @PostMapping("/{taskId}/status/change")
    public ResponseEntity<?> changeTaskStatus(@PathVariable int taskId,
                                                @RequestBody CodeDto statusCode,
                                                @RequestHeader("Authorization") String token) {
        return taskService.changeTaskStatus(token, taskId, statusCode.getCode());
    }

    @PostMapping("/{taskId}/description/change")
    public ResponseEntity<?> changeTaskDescription(@PathVariable int taskId,
                                              @RequestBody String description,
                                              @RequestHeader("Authorization") String token) {
        return taskService.changeTaskDescription(token, taskId, description);
    }

    @PostMapping("/{taskId}/assign")
    public ResponseEntity<?> assignUser(@PathVariable int taskId,
                                                   @RequestBody String username,
                                                   @RequestHeader("Authorization") String token) {
        return taskService.assignUser(token, taskId, username);
    }
}
