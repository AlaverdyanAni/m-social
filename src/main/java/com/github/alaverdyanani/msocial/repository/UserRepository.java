package com.github.alaverdyanani.msocial.repository;

import com.github.alaverdyanani.msocial.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional <User> findByChatId(Long chatId);
    @Query(value = "SELECT u.chatId FROM User u")
    List<Long> getAllChatIds();
}
