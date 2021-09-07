package com.urso.chat.repository;

import com.urso.chat.entity.Chat;
import com.urso.chat.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {
}
