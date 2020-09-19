package com.star_trello.darkside;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class DarkSideApplication {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(DarkSideApplication.class, args);
    }

}
