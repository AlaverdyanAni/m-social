package com.github.alaverdyanani.msocial.repository;

import com.github.alaverdyanani.msocial.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional <User> findByChatId(Long id);
}
