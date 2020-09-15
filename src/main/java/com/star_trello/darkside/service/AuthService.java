package com.star_trello.darkside.service;

import com.star_trello.darkside.model.User;
import com.star_trello.darkside.model.UserSession;
import com.star_trello.darkside.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class AuthService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    UserSessionService userSessionService;

    @Transactional
    public ResponseEntity<?> login(User user) {
        User loginUser = userRepo.getUserByEmailAndPassword(user.getEmail(), encodePassword(user.getPassword()));

        if (loginUser == null) {
            return badRequest("Invalid email or password");
        }

        UserSession session = userSessionService.createSession(loginUser.getId());
        return ResponseEntity.ok(session);
    }

    @Transactional
    public ResponseEntity<?> register(User user) {
        if (userRepo.existsUserByEmailOrUsername(user.getEmail(), user.getUsername())) {
            return badRequest("Invalid email or username");
        }
        user.setPassword(encodePassword(user.getPassword()));
        user.setCreated(new Date().getTime());

        userRepo.save(user);
        userRepo.flush();

        int userId = userRepo.getUserIdByEmail(user.getEmail()).getId();

        UserSession session = userSessionService.createSession(userId);

        return ResponseEntity.ok(session);
    }

    @Transactional
    public ResponseEntity<?> logout(UserSession userSession) {
        return userSessionService.logout(userSession);
    }

    private ResponseEntity<String> badRequest(String message) {
        return ResponseEntity.badRequest().body(message);
    }

    public String encodePassword(String password) {
        final int X = 4;
        String res = "";

        for (int i = 0; i < password.length(); i++) {
            long current = password.charAt(i) * (long) Math.pow(X, i);
            res = res.concat(current + "");
        }

        return res;
    }
}
