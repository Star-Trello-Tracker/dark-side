package com.star_trello.darkside.repo;

import com.star_trello.darkside.model.Queue;
import com.star_trello.darkside.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public interface QueueRepo extends JpaRepository<Queue, Integer> {

    boolean existsByTitle(String title);
    Queue getByTitle(String title);

    default List<String> getAllTitles() {
        return findAll().stream().map(queue -> queue.getTitle()).collect(Collectors.toList());
    }

    default List<Task> getTasksByQueueTitle(String title) {
        return getByTitle(title).getTaskList();
    }

    default Queue getQueueInfo(String title) {
        Queue queue = getByTitle(title);
        queue.setCreator(null);

        return queue;
    }

}
