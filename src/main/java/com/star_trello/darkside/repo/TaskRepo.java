package com.star_trello.darkside.repo;

import com.star_trello.darkside.model.Task;
import com.star_trello.darkside.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Collectors;

public interface TaskRepo extends JpaRepository<Task, Integer> {
    Task getById(int id);

    Task getTaskByKey(String key);

    boolean existsByKey(String key);

    Task getByKey(String key);

    default List<Task> getTasksByObserver(User user) {
        return findAll().stream().filter(task -> task.getObservers().contains(user)).collect(Collectors.toList());
    }

    List<Task> getTasksByCreator(User user);

    List<Task> getTasksByAssignee(User user);
}
