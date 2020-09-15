package com.star_trello.darkside.service;

import com.star_trello.darkside.model.User;
import com.star_trello.darkside.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    public User getUserById(int id) {
        return userRepo.getUserById(id);
    }
}
