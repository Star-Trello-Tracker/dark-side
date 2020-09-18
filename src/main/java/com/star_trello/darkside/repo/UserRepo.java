package com.star_trello.darkside.repo;

import com.star_trello.darkside.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    boolean existsUserByEmailOrUsername(String email, String username);

    User getUserIdByEmail(String email);

    User getUserById(int id);

    User getUserByEmailAndPassword(String email, String password);

    default List<String> getAllUsernames() {
        return findAll().stream().map(User::getUsername).collect(Collectors.toList());
    }

    User getUserByUsername(String username);

    default List<String[]> getAllNamesAndUsernames() {
        return findAll().stream().map(user -> new String[]{ user.getUsername(),
                user.getName(),
                user.getSurname()
        }).collect(Collectors.toList());
    }
}
