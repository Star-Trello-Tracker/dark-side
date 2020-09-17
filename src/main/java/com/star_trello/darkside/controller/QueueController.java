package com.star_trello.darkside.controller;

import com.star_trello.darkside.dto.QueueCreationDto;
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
    public ResponseEntity<?> getAllQueues(@RequestHeader("Authorization") String token) {
        return queueService.getAllQueues(token);
    }

    @PostMapping("")
    public ResponseEntity<?> createQueue(@RequestBody QueueCreationDto request, @RequestHeader("Authorization") String token) {
        return queueService.createQueue(token, request);
    }

    @GetMapping("/titles")
    public ResponseEntity<?> getQueuesTitles(@RequestHeader("Authorization") String token) {
        return queueService.getAllQueueTitles(token);
    }

    @GetMapping("/{title}")
    public ResponseEntity<?> getQueueByTitle(@RequestHeader("Authorization") String token, @PathVariable String title) {
        return queueService.getQueueByTitle(token, title.toUpperCase());
    }

    @GetMapping("/{title}/tasks")
    public ResponseEntity<?> getQueueTasks(@RequestHeader("Authorization") String token, @PathVariable String title) {
        return queueService.getQueueTasks(token, title.toUpperCase());
    }

}
