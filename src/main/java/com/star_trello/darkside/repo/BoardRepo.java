package com.star_trello.darkside.repo;

import com.star_trello.darkside.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepo extends JpaRepository<Board, Integer> {
    Board findById(int id);
}
