package com.star_trello.darkside.telegram_bot.services;

import com.star_trello.darkside.repo.UserRepo;
import com.star_trello.darkside.telegram_bot.model.TelegramUser;
import com.star_trello.darkside.telegram_bot.repo.TelegramUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubscribeService {
    @Autowired
    TelegramUserRepo telegramUserRepo;

    @Transactional
    public void subscribeUser(String tgUsername, Long chatId) {
        TelegramUser user;
        if (!telegramUserRepo.existsByTgUsername(tgUsername)) {
            user = TelegramUser.builder()
                    .tgUsername(tgUsername)
                    .chatId(chatId)
                    .subscribed(true)
                    .build();
        }
        else {
            user = telegramUserRepo.getByTgUsername(tgUsername);
            user.setSubscribed(true);
        }
        telegramUserRepo.save(user);
    }
    @Transactional
    public void unsubscribeUser(String tgUsername, Long chatId) {
        TelegramUser user;
        if (!telegramUserRepo.existsByTgUsername(tgUsername)) {
            user = TelegramUser.builder()
                    .tgUsername(tgUsername)
                    .chatId(chatId)
                    .subscribed(false)
                    .build();
        }
        else {
            user = telegramUserRepo.getByTgUsername(tgUsername);
            user.setSubscribed(false);

        }
        telegramUserRepo.save(user);
    }

    public boolean isUserSubscribed(String tgUsername) {
        return telegramUserRepo.existsByTgUsername(tgUsername) &&
                telegramUserRepo.isSubscribedByTgUsername(tgUsername);
    }
    public Long getChatIdByTgUsername(String tgUsername) {
        return telegramUserRepo.getChatIdByTgUsername(tgUsername);
    }



}
