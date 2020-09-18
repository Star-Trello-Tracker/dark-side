package com.star_trello.darkside.controller;

import com.star_trello.darkside.dto.QueueCreationDto;
import com.star_trello.darkside.model.User;
import com.star_trello.darkside.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/queues")
public class QueueController {
    @Autowired
    QueueService queueService;

    @GetMapping("")
    public ResponseEntity<?> getAllQueues() {
        return queueService.getAllQueues();
    }

    @PostMapping("")
    public ResponseEntity<?> createQueue(@RequestAttribute("user") User user, @RequestBody QueueCreationDto request) {
        return queueService.createQueue(user, request);
    }

    @GetMapping("/titles")
    public ResponseEntity<?> getQueuesTitles() {
        return queueService.getAllQueueTitles();
    }

    @GetMapping("/{title}")
    public ResponseEntity<?> getQueueByTitle(@PathVariable String title) {
        return queueService.getQueueByTitle(title.toUpperCase());
    }

    @GetMapping("/{title}/tasks")
    public ResponseEntity<?> getQueueTasks(@PathVariable String title) {
        return queueService.getQueueTasks(title.toUpperCase());
    }

}
