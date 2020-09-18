package com.star_trello.darkside.repo;

import com.star_trello.darkside.model.Notification;
import com.star_trello.darkside.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepo extends JpaRepository<Notification, Integer> {
    List<Notification> getAllByCalledUser(User calledUser);
    Notification getById(int id);
}
