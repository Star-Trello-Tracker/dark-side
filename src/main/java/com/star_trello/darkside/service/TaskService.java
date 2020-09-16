package com.star_trello.darkside.service;

import com.star_trello.darkside.model.Task;
import com.star_trello.darkside.repo.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    @Autowired
    TaskRepo taskRepo;


    public ResponseEntity<?> create(Task task) {
        taskRepo.save(task);
        taskRepo.flush();

        return ResponseEntity.ok().build();
    }
}
