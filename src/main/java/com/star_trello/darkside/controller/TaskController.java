package com.star_trello.darkside.controller;

import com.star_trello.darkside.dto.CodeDto;
import com.star_trello.darkside.dto.TaskCreationDto;
import com.star_trello.darkside.model.Task;
import com.star_trello.darkside.model.User;
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
    public ResponseEntity<?> createTask(@RequestAttribute("user") User user,
                                            @RequestBody TaskCreationDto request) {
        System.out.println(user);
        return taskService.createTask(user, request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable int id, @RequestHeader("Authorization") String token) {
        return taskService.getTaskById(id);
    }

    @PostMapping("/{taskId}/priority/change")
    public ResponseEntity<?> changeTaskPriority(@RequestAttribute("task") Task task,
                                                @RequestBody CodeDto priorityCode) {
        return taskService.changeTaskPriority(task, priorityCode.getCode());
    }

    @PostMapping("/{taskId}/status/change")
    public ResponseEntity<?> changeTaskStatus(@RequestAttribute("task") Task task,
                                                @RequestBody CodeDto statusCode) {
        return taskService.changeTaskStatus(task, statusCode.getCode());
    }

    @PostMapping("/{taskId}/description/change")
    public ResponseEntity<?> changeTaskDescription(@RequestAttribute("task") Task task,
                                              @RequestBody String description) {
        return taskService.changeTaskDescription(task, description);
    }
}
