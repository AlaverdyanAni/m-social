package com.github.alaverdyanani.msocial.service;

import com.github.alaverdyanani.msocial.entity.Message;
import com.github.alaverdyanani.msocial.entity.User;
import com.github.alaverdyanani.msocial.repository.MessageRepository;
import com.github.alaverdyanani.msocial.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public void saveMessage(Long chatId, String text) {
        Optional<User> user = userRepository.findByChatId(chatId);
        Message message = Message.builder()
                .user(user.get())
                .request(text)
                .response("TelegramBot response")
                .build();
        messageRepository.save(message);
        log.info("{} Сохранен в базе данных", message);
    }
}
