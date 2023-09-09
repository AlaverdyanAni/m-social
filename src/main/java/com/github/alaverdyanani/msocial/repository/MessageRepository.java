package com.github.alaverdyanani.msocial.repository;

import com.github.alaverdyanani.msocial.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {
}
