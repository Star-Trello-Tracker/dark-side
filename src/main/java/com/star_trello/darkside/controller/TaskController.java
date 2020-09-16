package com.star_trello.darkside.controller;

import com.star_trello.darkside.model.Task;
import com.star_trello.darkside.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {
    @Autowired
    TaskService taskService;

    @PostMapping("/task/create")
    public ResponseEntity<?> register(@RequestBody Task task) {
        return taskService.create(task);
    }

}
