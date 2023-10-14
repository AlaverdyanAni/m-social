package com.github.alaverdyanani.msocial.service;

import com.github.alaverdyanani.msocial.entity.User;
import com.github.alaverdyanani.msocial.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public  void createUser(Long chatId){
        com.github.alaverdyanani.msocial.entity.User user = com.github.alaverdyanani.msocial.entity.User.builder()
                .chatId(chatId)
                .lastMessageAt(Instant.now())
                        .build();
        userRepository.save(user);
        log.info("New user created");

    }

    public  void updateMessageTime(User user){
        user.setLastMessageAt(Instant.now());
        userRepository.save(user);
        log.info("User last message updated");

    }

    public  Optional<User> findByChatId(long chatid){
       return userRepository.findByChatId(chatid);
    }

    public List<Long> getAllUsersChatIds(){
        log.info("All chats called");
        return userRepository.getAllChatIds();
    }

}
