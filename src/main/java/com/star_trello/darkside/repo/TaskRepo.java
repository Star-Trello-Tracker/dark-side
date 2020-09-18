package com.star_trello.darkside.repo;

import com.star_trello.darkside.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepo extends JpaRepository<Task, Integer> {
    Task getById(int id);
}
