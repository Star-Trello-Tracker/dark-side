package com.star_trello.darkside.repo;

import com.star_trello.darkside.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepo extends JpaRepository<Task, Integer> {

    Task getTaskById(int id);
}
