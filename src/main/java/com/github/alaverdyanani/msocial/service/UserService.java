package com.github.alaverdyanani.msocial.service;

import com.github.alaverdyanani.msocial.entity.User;
import com.github.alaverdyanani.msocial.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public String saveUser(Long chatId) {
        Optional<User> userOrEmpty = userRepository.findByChatId(chatId);
        if (userOrEmpty.isEmpty()) {
            User user = User.builder()
                    .chatId(chatId)
                    .lastMessageAt(Instant.now())
                    .build();
            userRepository.save(user);
            log.info("{} Сохранен в базе данных", user);
        } else {
            User user = userOrEmpty.get();
            user.setLastMessageAt(Instant.now());
            userRepository.save(user);
            log.info("{} Изменен в базе данных", user);
        }
        return "Congratulations! You have successfully registered.";
    }
}
