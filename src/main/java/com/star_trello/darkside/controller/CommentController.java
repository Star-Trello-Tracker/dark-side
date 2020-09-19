package com.star_trello.darkside.controller;

import com.star_trello.darkside.dto.CommentCreationDto;
import com.star_trello.darkside.model.User;
import com.star_trello.darkside.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<?> createComment(@RequestAttribute("user") User user,
                                           @RequestBody CommentCreationDto request) {
        return commentService.create(user, request);
    }

    @PostMapping("/{commentId}/edit")
    public ResponseEntity<?> editComment(@RequestAttribute("user") User user,
                                         @PathVariable int commentId,
                                         @RequestBody CommentCreationDto request) {
        return commentService.edit(user, commentId, request);
    }

    @PostMapping("/{commentId}/delete")
    public ResponseEntity<?> deleteComment(@RequestAttribute("user") User user,
                                           @PathVariable int commentId) {
        return commentService.delete(user, commentId);
    }
}
