package com.urso.chat.repository;

import com.urso.chat.entity.Chat;
import com.urso.chat.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {

    List<ChatMessage> findByChat(Chat c);
}
