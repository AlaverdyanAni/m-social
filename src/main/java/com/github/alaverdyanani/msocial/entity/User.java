package com.github.alaverdyanani.msocial.entity;

import lombok.*;
import javax.persistence.*;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "last_message_at")
    private Instant lastMessageAt;

}