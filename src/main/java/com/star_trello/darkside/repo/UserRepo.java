package com.star_trello.darkside.repo;

import com.star_trello.darkside.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    boolean existsUserByEmailOrUsername(String email, String username);

    User getUserIdByEmail(String email);

    User getUserById(int id);

    User getUserByEmailAndPassword(String email, String password);
}
