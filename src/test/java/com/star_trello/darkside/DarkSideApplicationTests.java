package com.star_trello.darkside;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class DarkSideApplicationTests {

    @Test
    void contextLoads() {
        boolean x = true;
        Assert.isTrue(x);
    }

}
