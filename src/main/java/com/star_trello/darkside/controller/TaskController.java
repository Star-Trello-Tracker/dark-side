package com.star_trello.darkside.controller;

import com.star_trello.darkside.dto.CodeDto;
import com.star_trello.darkside.dto.TaskCreationDto;
import com.star_trello.darkside.dto.TextDto;
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
        return taskService.createTask(user, request);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{key}")
    public ResponseEntity<?> getTaskByKey(@PathVariable String key) {
        return taskService.getTaskByKey(key);
    }

    @PostMapping("/{taskId}/priority/change")
    public ResponseEntity<?> changeTaskPriority(@RequestAttribute("user") User user,
                                                @RequestAttribute("task") Task task,
                                                @RequestBody CodeDto priorityCode) {
        return taskService.changeTaskPriority(user, task, priorityCode.getCode());
    }

    @PostMapping("/{taskId}/status/change")
    public ResponseEntity<?> changeTaskStatus(@RequestAttribute("user") User user,
                                              @RequestAttribute("task") Task task,
                                              @RequestBody CodeDto statusCode) {
        return taskService.changeTaskStatus(user, task, statusCode.getCode());
    }

    @PostMapping("/{taskId}/title/change")
    public ResponseEntity<?> changeTaskTitle(@RequestAttribute("user") User user,
                                             @RequestAttribute("task") Task task,
                                             @RequestBody TextDto textDto) {
        return taskService.changeTaskTitle(user, task, textDto.getText());
    }

    @PostMapping("/{taskId}/description/change")
    public ResponseEntity<?> changeTaskDescription(@RequestAttribute("user") User user,
                                                   @RequestAttribute("task") Task task,
                                                   @RequestBody TextDto textDto) {
        return taskService.changeTaskDescription(user, task, textDto.getText());
    }

    @PostMapping("/{taskId}/assign")
    public ResponseEntity<?> assignUser(@RequestAttribute("user") User user,
                                        @RequestAttribute("task") Task task,
                                        @RequestBody TextDto textDto) {
        return taskService.assignUser(user, task, textDto.getText());
    }

    @PostMapping("/{taskId}/observe")
    public ResponseEntity<?> setObserver(@RequestAttribute("user") User user,
                                         @RequestAttribute("task") Task task) {
        return taskService.setObserver(user, task);
    }
}
