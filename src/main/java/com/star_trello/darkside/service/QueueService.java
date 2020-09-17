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
import java.util.List;
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
    public ResponseEntity<?> getAllQueues(String token) {
        if (!userSessionRepo.existsByToken(token)) {
            return ResponseEntity.status(401).build();
        }
        List<Queue> queues = queueRepo.findAll();
        return ResponseEntity.ok().body(queues);
    }

    @Transactional
    public ResponseEntity<?> createQueue(String token, QueueCreationDto request) {
        User creator = userSessionService.getUserByToken(token);
        if (creator == null) {
            return ResponseEntity.status(401).build();
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

    @Transactional
    public ResponseEntity<?> getAllQueueTitles(String token) {
        if (!userSessionRepo.existsByToken(token)) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok().body(queueRepo.getAllTitles());
    }

    @Transactional
    public ResponseEntity<?> getQueueTasks(String token, String title) {
        if (!userSessionRepo.existsByToken(token)) {
            return ResponseEntity.status(401).build();
        }
        if (!queueRepo.existsByTitle(title)) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok().body(queueRepo.getTasksByQueueTitle(title));
    }

    @Transactional
    public ResponseEntity<?> getQueueByTitle(String token, String title) {
        if (!userSessionRepo.existsByToken(token)) {
            return ResponseEntity.status(401).build();
        }
        if (!queueRepo.existsByTitle(title)) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok().body(queueRepo.getByTitle(title));
    }
}
