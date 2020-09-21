package com.star_trello.darkside.controller;

import com.star_trello.darkside.dto.TextDto;
import com.star_trello.darkside.model.User;
import com.star_trello.darkside.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
public class BoardController {

    @Autowired
    BoardService boardService;

    @GetMapping("/all")
    public ResponseEntity<?> addTaskToBoard() {
        return boardService.getAll();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createBoard(
            @RequestAttribute("user") User user,
            @RequestBody TextDto textDto
    ) {
        return boardService.createBoard(user, textDto.getText());
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<?> getBoardById(
            @PathVariable int boardId
    ) {
        return boardService.getBoardById(boardId);
    }

    @PostMapping("/{boardId}/add")
    public ResponseEntity<?> addTaskToBoard(
            @RequestAttribute("user") User user,
            @PathVariable int boardId,
            @RequestBody TextDto textDto
    ) {
        return boardService.addTaskToBoard(user, boardId, textDto.getText());
    }
}
