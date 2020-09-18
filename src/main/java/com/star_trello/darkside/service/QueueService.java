package com.star_trello.darkside.service;

import com.star_trello.darkside.dto.QueueCreationDto;
import com.star_trello.darkside.model.Queue;
import com.star_trello.darkside.model.User;
import com.star_trello.darkside.repo.QueueRepo;
import com.star_trello.darkside.repo.UserSessionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class QueueService {
    @Autowired
    QueueRepo queueRepo;

    @Autowired
    UserSessionRepo userSessionRepo;

    @Autowired
    UserSessionService userSessionService;

    @Transactional
    public ResponseEntity<?> getAllQueues() {
        List<Queue> queues = queueRepo.findAll();
        return ResponseEntity.ok().body(queues);
    }

    @Transactional
    public ResponseEntity<?> createQueue(User user, QueueCreationDto request) {
        Queue queue = Queue.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .creator(user)
                .taskList(new ArrayList<>())
                .build();
        queueRepo.saveAndFlush(queue);
        return ResponseEntity.ok().body(queue);
    }

    @Transactional
    public ResponseEntity<?> getAllQueueTitles() {
        return ResponseEntity.ok().body(queueRepo.getAllTitles());
    }

    @Transactional
    public ResponseEntity<?> getQueueTasks(String title) {
        return ResponseEntity.ok().body(queueRepo.getTasksByQueueTitle(title));
    }

    @Transactional
    public ResponseEntity<?> getQueueByTitle(String title) {
        return ResponseEntity.ok().body(queueRepo.getByTitle(title));
    }
}
