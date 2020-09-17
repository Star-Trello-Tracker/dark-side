package com.star_trello.darkside.service;

import com.star_trello.darkside.dto.EditingProfileDto;
import com.star_trello.darkside.model.User;
import com.star_trello.darkside.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    UserSessionService userSessionService;

    public User getUserById(int id) {
        return userRepo.getUserById(id);
    }

    public ResponseEntity<?> getUserByIdWrapper(int id) {
        User user = getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    public ResponseEntity<?> updateUserProfile(String token, EditingProfileDto editingProfile) {
        User user = userSessionService.getUserByToken(token);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        user.setName(editingProfile.getName());
        user.setSurname(editingProfile.getSurname());
        user.setTgUsername(editingProfile.getTgUsername());

        userRepo.save(user);
        userRepo.flush();

        return ResponseEntity.ok(user);
    }

    public ResponseEntity<?> getAllUsernames() {
        return ResponseEntity.ok(userRepo.getAllUsernames());
    }
}
