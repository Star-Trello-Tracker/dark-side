package com.star_trello.darkside.service;

import com.star_trello.darkside.model.Board;
import com.star_trello.darkside.model.Task;
import com.star_trello.darkside.model.User;
import com.star_trello.darkside.repo.BoardRepo;
import com.star_trello.darkside.repo.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Service
public class BoardService {

    @Autowired
    BoardRepo boardRepo;

    @Autowired
    TaskRepo taskRepo;

    @Transactional
    public ResponseEntity<?> createBoard(User user, String title) {

        Board board = Board
                .builder()
                .creator(user)
                .title(title)
                .tasks(new HashSet<>())
                .build();

        boardRepo.saveAndFlush(board);
        return ResponseEntity.ok(board);
    }

    @Transactional
    public ResponseEntity<?> addTaskToBoard(User user, int boardId, String key) {

        Board board = boardRepo.findById(boardId);

        if (!user.equals(board.getCreator())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Task task = taskRepo.getByKey(key.toUpperCase());

        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (task.getBoardName() != null) {
            return ResponseEntity.badRequest().build();
        }

        board.getTasks().add(task);
        boardRepo.save(board);
        boardRepo.flush();

        task.setBoardId(board.getId());
        task.setBoardName(board.getTitle());
        taskRepo.save(task);

        return ResponseEntity.ok(board);
    }

    @Transactional
    public ResponseEntity<?> getBoardById(int id) {
        Board board = boardRepo.findById(id);

        if (board == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(board);
    }

    @Transactional
    public ResponseEntity<?> getAll() { return ResponseEntity.ok(boardRepo.findAll()); }
}
