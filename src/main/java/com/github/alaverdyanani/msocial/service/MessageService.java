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

    public  void createSendMessage (String sendMessage, long userChatId){
        Optional<User> userOptional = userRepository.findByChatId(userChatId);
        if (userOptional.isEmpty()){
            return;
        }
        Message message = Message.builder()
                .user(userOptional.get())
                .response(sendMessage)
                .build();
        messageRepository.save(message);
        log.info("Outgoing message saved");
    }

    public void createReceivedMessage(String receivedMessage, long userChatId) {
        Optional<User> optionalUser = userRepository.findByChatId(userChatId);
        if (optionalUser.isEmpty()) {
            return;
        }
        Message message = Message.builder()
                .user(optionalUser.get())
                .request(receivedMessage)
                .build();
        messageRepository.save(message);
        log.info("Incoming message saved");
    }
}
