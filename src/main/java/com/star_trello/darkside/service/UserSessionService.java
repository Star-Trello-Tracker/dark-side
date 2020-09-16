package com.star_trello.darkside.service;

import com.star_trello.darkside.model.User;
import com.star_trello.darkside.model.UserSession;
import com.star_trello.darkside.repo.UserSessionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Random;

@Service
public class UserSessionService {

    @Autowired
    UserSessionRepo userSessionRepo;

    @Autowired
    UserService userService;

    public String generateToken() {
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 64;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            if (randomLimitedInt % 2 == 0) {
                buffer.append(randomLimitedInt / 3);
            }
            buffer.append((char) randomLimitedInt);
        }

        return buffer.toString();
    }

    @Transactional
    public UserSession createSession(int id) {
        String token = this.generateToken();

        UserSession session = UserSession.builder().userId(id).token(token).build();
        userSessionRepo.save(session);

        return session;
    }

    @Transactional
    public ResponseEntity<?> logout(UserSession userSession) {
        userSessionRepo.delete(userSession);
        userSessionRepo.flush();

        return ResponseEntity.ok().build();
    }

    @Transactional
    public User getUserByToken(String token) {
        boolean isAuth = userSessionRepo.existsByToken(token);

        if (!isAuth) {
            return null;
        }

        return userService.getUserById(userSessionRepo.getUserSessionByToken(token).getUserId());
    }
}
