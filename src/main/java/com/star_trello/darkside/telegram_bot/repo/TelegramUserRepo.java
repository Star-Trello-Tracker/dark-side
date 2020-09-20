package com.star_trello.darkside.telegram_bot.repo;

import com.star_trello.darkside.telegram_bot.model.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelegramUserRepo extends JpaRepository<TelegramUser, Integer> {
    boolean existsByTgUsername(String username);
    TelegramUser getByTgUsername(String username);
    default boolean isSubscribedByTgUsername(String tgUsername) {
        return getByTgUsername(tgUsername).isSubscribed();
    }
    default Long getChatIdByTgUsername(String tgUsername) {
        return getByTgUsername(tgUsername).getChatId();
    }

}
