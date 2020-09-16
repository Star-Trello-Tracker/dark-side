package com.star_trello.darkside.repo;

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
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@DataJpaTest
class QueueRepoTest {
    private static final String TITLE1 = "FRONT";
    private static final String DESCRIPTION1 = "Front tasks queue";
    private static final String TITLE2 = "BACK";
    private static final String DESCRIPTION2 = "Back tasks queue";
    private static final String MAIL = "1van@mail.ru";
    private static final String USERNAME = "1van";
    private static final String PASSWORD = "123";

    @Autowired
    QueueRepo queueRepo;
    @Autowired
    UserRepo userRepo;

    @BeforeEach
    @Transactional
    @Rollback(false)
    public void setUp() {
        User user  = User.builder()
                .email(MAIL)
                .username(USERNAME)
                .password(PASSWORD)
                .build();
        userRepo.save(user);
        queueRepo.save(
                Queue.builder()
                    .title(TITLE1)
                    .description(DESCRIPTION1)
                    .taskList(new ArrayList<>())
                    .creator(user)
                    .build());
        queueRepo.save(
                Queue.builder()
                        .title(TITLE2)
                        .description(DESCRIPTION2)
                        .taskList(new ArrayList<>())
                        .creator(user)
                        .build());
    }

    @Test
    void getAllTitles() {
        List<String> titles = new ArrayList<>();
        titles.add(TITLE1);
        titles.add(TITLE2);
        Assert.assertEquals(titles, queueRepo.getAllTitles());
    }

    @Test
    void existsByTitle() {
        Assert.assertTrue(queueRepo.existsByTitle(TITLE1));
    }

    @Test
    void getByTitle() {

    }

    @Test
    void getTasksByQueueTitle() {
    }
}