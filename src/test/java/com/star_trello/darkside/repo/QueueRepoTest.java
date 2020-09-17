package com.star_trello.darkside.repo;

import com.star_trello.darkside.constants.QueueTestConstants;
import com.star_trello.darkside.constants.UserTestConstants;
import com.star_trello.darkside.model.Queue;
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

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
class QueueRepoTest {

    @Autowired
    QueueRepo queueRepo;
    @Autowired
    UserRepo userRepo;

    @BeforeEach
    @Transactional
    @Rollback(false)
    public void setUp() {
        User user  = User.builder()
                .email(UserTestConstants.MAIL)
                .username(UserTestConstants.USERNAME)
                .password(UserTestConstants.PASSWORD)
                .build();
        userRepo.save(user);
        queueRepo.save(
                Queue.builder()
                    .title(QueueTestConstants.TITLE1)
                    .description(QueueTestConstants.DESCRIPTION1)
                    .taskList(new ArrayList<>())
                    .creator(user)
                    .build());
        queueRepo.save(
                Queue.builder()
                        .title(QueueTestConstants.TITLE2)
                        .description(QueueTestConstants.DESCRIPTION2)
                        .taskList(new ArrayList<>())
                        .creator(user)
                        .build());
    }

    @Test
    void getAllTitles() {
        List<String> titles = new ArrayList<>();
        titles.add(QueueTestConstants.TITLE1);
        titles.add(QueueTestConstants.TITLE2);
        Assert.assertEquals(titles, queueRepo.getAllTitles());
    }

    @Test
    void existsByTitle() {
        Assert.assertTrue(queueRepo.existsByTitle(QueueTestConstants.TITLE1));
    }

    @Test
    void getTasksByQueueTitle() {
        //todo after tasks are done
    }
}