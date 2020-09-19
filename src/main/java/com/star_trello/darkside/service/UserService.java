package com.star_trello.darkside.service;

import com.star_trello.darkside.dto.EditingProfileDto;
import com.star_trello.darkside.model.User;
import com.star_trello.darkside.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    UserSessionService userSessionService;

    public User getUserById(int id) {
        return userRepo.getUserById(id);
    }

    @Transactional
    public ResponseEntity<?> getUserByIdWrapper(int id) {
        User user = getUserById(id);
        if (user == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.ok(user);
    }

    @Transactional
    public ResponseEntity<?> updateUserProfile(String token, EditingProfileDto editingProfile) {
        User user = userSessionService.getUserByToken(token);
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        user.setName(editingProfile.getName());
        user.setSurname(editingProfile.getSurname());
        user.setTgUsername(editingProfile.getTgUsername());

        userRepo.save(user);
        userRepo.flush();

        return ResponseEntity.ok(user);
    }

    @Transactional
    public User getUserByUsername(String username) {
        if (!this.userRepo.existsByUsername(username)) {
            return null;
        }

        return this.userRepo.getUserByUsername(username);
    }

    @Transactional
    public ResponseEntity<?> getAllUsernames() {
        return ResponseEntity.ok(userRepo.getAllUsernames());
    }

    @Transactional
    public ResponseEntity<?> getAllNamesAndUsernames() {
        return ResponseEntity.ok(userRepo.getAllNamesAndUsernames());
    }
}
