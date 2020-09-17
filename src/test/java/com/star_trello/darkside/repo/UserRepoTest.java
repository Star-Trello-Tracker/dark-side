package com.star_trello.darkside.repo;

import com.star_trello.darkside.constants.UserTestConstants;
import com.star_trello.darkside.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.star_trello.darkside.constants.UserTestConstants.USERNAME;

@RunWith(SpringRunner.class)
@DataJpaTest
class UserRepoTest {

    @Autowired
    UserRepo userRepo;

    @BeforeEach
    @Transactional
    @Rollback(false)
    public void setUp() {
        userRepo.save(
                User.builder()
                        .email(UserTestConstants.MAIL)
                        .username(USERNAME)
                        .password(UserTestConstants.PASSWORD)
                        .build()
        );
    }

    @Test
    void existsUserByEmailOrUsername() {
        Assert.assertTrue(userRepo.existsUserByEmailOrUsername(UserTestConstants.MAIL, USERNAME));
    }

    @Test
    void getUserIdByEmail() {
        User user = userRepo.getUserByEmailAndPassword(UserTestConstants.MAIL, UserTestConstants.PASSWORD);
        Assert.assertEquals(user.getId(), userRepo.getUserIdByEmail(UserTestConstants.MAIL).getId());
    }

    @Test
    void getUserById() {
        User user = userRepo.getUserByEmailAndPassword(UserTestConstants.MAIL, UserTestConstants.PASSWORD);
        Assert.assertEquals(user, userRepo.getUserById(user.getId()));
    }

    @Test
    void getUserByEmailAndPassword() {
        User user = userRepo.getUserById(userRepo.getUserIdByEmail(UserTestConstants.MAIL).getId());
        Assert.assertEquals(user, userRepo.getUserByEmailAndPassword(UserTestConstants.MAIL, UserTestConstants.PASSWORD));
    }

    @Test
    void getAllUsernames() {
        List<String> allUsernames = userRepo.getAllUsernames();
        Assert.assertEquals(1, allUsernames.size());
        Assert.assertEquals(USERNAME, allUsernames.get(0));
    }
}