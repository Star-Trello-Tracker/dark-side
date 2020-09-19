package com.star_trello.darkside.controller;

import com.star_trello.darkside.dto.CommentCreationDto;
import com.star_trello.darkside.dto.TextDto;
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

    @PostMapping("/edit/{commentId}")
    public ResponseEntity<?> editComment(@RequestAttribute("user") User user,
                                         @PathVariable int commentId,
                                         @RequestBody TextDto textDto) {
        return commentService.edit(user, commentId, textDto.getText());
    }

//    @DeleteMapping("/delete/{commentId}")
//    public ResponseEntity<?> deleteComment(@PathVariable int commentId, @RequestHeader("Authorization") String token) {
//        return commentService.delete(token, commentId);
//    }
}
