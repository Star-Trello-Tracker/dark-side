package com.star_trello.darkside.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class AuthServiceTest {

    @Test
    void encodePassword() {
        Assert.assertEquals("49200816", new AuthService().encodePassword("123"));
    }
}