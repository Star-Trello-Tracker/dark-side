package com.star_trello.darkside.service;

import com.star_trello.darkside.dto.CommentCreationDto;
import com.star_trello.darkside.model.Comment;
import com.star_trello.darkside.model.Task;
import com.star_trello.darkside.model.User;
import com.star_trello.darkside.repo.CommentRepo;
import com.star_trello.darkside.repo.TaskRepo;
import com.star_trello.darkside.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    CommentRepo commentRepo;

    @Autowired
    TaskService taskService;

    @Autowired
    TaskRepo taskRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    UserSessionService userSessionService;

    @Transactional
    public ResponseEntity<?> create(User creator, CommentCreationDto request) {
        Task task = taskRepo.getByKey(request.getTaskKey());
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task with id " + request.getTaskKey() + " doesn't exist.");
        }

        List<User> whoCalledUsers = request.getWhoCalled().stream()
                .map(userRepo::getUserByUsername)
                .collect(Collectors.toList());

        Comment comment = Comment.builder()
                .creator(creator)
                .text(request.getComment())
                .task(task)
                .whoCalled(whoCalledUsers)
                .created(System.currentTimeMillis())
                .build();

        commentRepo.save(comment);
        task.getComments().add(comment);
        taskRepo.save(task);

        return ResponseEntity.ok(comment);
    }

    @Transactional
    public ResponseEntity<?> edit(User creator, int commentId, String text) {
        Comment comment = commentRepo.getById(commentId);
        if ((comment != null) && (!comment.getCreator().equals(creator))) {
            return ResponseEntity.badRequest().body("Comment doesn't exist or it is not your comment");
        }

        comment.setText(text);
        commentRepo.save(comment);

        return ResponseEntity.ok(comment);
    }

    @Transactional
    public ResponseEntity<?> delete(String token, int commentId) {
        User creator = userSessionService.getUserByToken(token);
        if (creator == null) {
            return ResponseEntity.status(401).build();
        }

        Comment comment = commentRepo.getById(commentId);
        if ((comment != null) && (!comment.getCreator().equals(creator))) {
            return ResponseEntity.badRequest().body("Comment doesn't exist or it is not your comment");
        }

        commentRepo.delete(comment);
        return ResponseEntity.status(204).build();
    }
}
