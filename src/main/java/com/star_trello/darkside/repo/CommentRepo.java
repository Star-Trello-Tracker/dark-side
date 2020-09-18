package com.star_trello.darkside.repo;

import com.star_trello.darkside.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

    Comment getById(Integer id);
}
