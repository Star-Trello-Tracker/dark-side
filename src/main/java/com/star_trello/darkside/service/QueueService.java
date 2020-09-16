package com.star_trello.darkside.service;

import com.star_trello.darkside.dto.QueueCreationDto;
import com.star_trello.darkside.model.Queue;
import com.star_trello.darkside.model.User;
import com.star_trello.darkside.repo.QueueRepo;
import com.star_trello.darkside.repo.UserRepo;
import com.star_trello.darkside.repo.UserSessionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class QueueService {
    @Autowired
    QueueRepo queueRepo;
    @Autowired
    UserSessionRepo userSessionRepo;
    @Autowired
    UserSessionService userSessionService;

    @Transactional
    public ResponseEntity<?> createQueue(String token, QueueCreationDto request) {
        User creator = userSessionService.getUserByToken(token);
        if (creator == null) {
            return ResponseEntity.badRequest().body("Token is invalid.");
        }
        Queue queue = Queue.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .creator(creator)
                .taskList(new ArrayList<>())
                .build();
        queueRepo.saveAndFlush(queue);
        return ResponseEntity.ok().body(queue);
    }

    public ResponseEntity<?> getAllQueueTitles(String token) {
        if (!userSessionRepo.existsByToken(token)) {
            return ResponseEntity.badRequest().body("Token is invalid.");
        }
        return ResponseEntity.ok().body(queueRepo.getAllTitles());
    }

    public ResponseEntity<?> getQueueTasks(String token, String title) {
        if (!userSessionRepo.existsByToken(token)) {
            return ResponseEntity.badRequest().body("Token is invalid.");
        }
        if (!queueRepo.existsByTitle(title)) {
            return ResponseEntity.badRequest().body(title + " queue doesn't exist.");
        }
        return ResponseEntity.ok().body(queueRepo.getTasksByQueueTitle(title));
    }
}
