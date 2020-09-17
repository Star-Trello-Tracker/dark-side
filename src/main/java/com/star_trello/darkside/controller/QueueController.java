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

    @PostMapping("/")
    public ResponseEntity<?> createQueue(@RequestBody QueueCreationDto request, @RequestHeader String token) {
        return queueService.createQueue(token, request);
    }

    @GetMapping("/titles")
    public ResponseEntity<?> getQueuesTitles(@RequestHeader String token) {
        return queueService.getAllQueueTitles(token);
    }

    @GetMapping("/{title}/tasks")
    public ResponseEntity<?> getQueueTasks(@RequestHeader String token, @PathVariable String title) {
        return queueService.getQueueTasks(token, title);
    }

}
