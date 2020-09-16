package com.star_trello.darkside.repo;

import com.star_trello.darkside.model.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSessionRepo extends JpaRepository<UserSession, Integer> {
    boolean existsByToken(String token);
}
