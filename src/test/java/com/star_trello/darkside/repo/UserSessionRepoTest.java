package com.star_trello.darkside.repo;

import com.star_trello.darkside.model.UserSession;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@DataJpaTest
class UserSessionRepoTest {
    private static final String TOKEN = "1231231231313";
    private static final int USER_ID = 1;

    @Autowired
    UserSessionRepo userSessionRepo;

    @BeforeEach
    @Transactional
    @Rollback(false)
    public void setUp() {
        userSessionRepo.save(UserSession.builder().token(TOKEN).userId(USER_ID).build());
    }

    @Test
    void existsByToken() {
        Assert.assertTrue(userSessionRepo.existsByToken(TOKEN));
    }
}