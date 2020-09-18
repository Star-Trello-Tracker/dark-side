package com.star_trello.darkside.controller;

import com.star_trello.darkside.dto.CommentCreationDto;
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
    public ResponseEntity<?> createComment(@RequestBody CommentCreationDto request, @RequestHeader("Authorization") String token) {
        return commentService.create(token, request);
    }

    @PostMapping("/edit/{commentId}")
    public ResponseEntity<?> editComment(@PathVariable int commentId,
                                                @RequestParam String text,
                                                @RequestHeader("Authorization") String token) {
        return commentService.edit(token, commentId, text);
    }

//    @DeleteMapping("/delete/{commentId}")
//    public ResponseEntity<?> deleteComment(@PathVariable int commentId, @RequestHeader("Authorization") String token) {
//        return commentService.delete(token, commentId);
//    }
}
