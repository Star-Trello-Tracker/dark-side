package com.star_trello.darkside.telegram_bot.services;

import com.star_trello.darkside.model.Task;
import com.star_trello.darkside.model.User;
import com.star_trello.darkside.repo.TaskRepo;
import com.star_trello.darkside.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TrackerService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    TaskRepo taskRepo;

    public boolean userExistsByTgUsername(String tgUsername) {
        return userRepo.existsByTgUsername(tgUsername);
    }

    public User getUserByTgUsername(String tgUsername) {
        return userRepo.getByTgUsername(tgUsername);
    }

    public List<Task> getObservedTasksForUser(String tgUsername) {
        User user = userRepo.getByTgUsername(tgUsername);
        return taskRepo.getTasksByObserver(user);
    }

    public List<Task> getCreatedTasksForUser(String tgUsername) {
        User user = userRepo.getByTgUsername(tgUsername);
        return taskRepo.getTasksByCreator(user);
    }

    public List<Task> getAssignedTasksForUser(String tgUsername) {
        User user = userRepo.getByTgUsername(tgUsername);
        return taskRepo.getTasksByAssignee(user);
    }
}
