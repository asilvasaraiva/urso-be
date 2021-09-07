package com.urso.chat.repository;

import com.urso.chat.entity.Chat;
import com.urso.chat.entity.ChatComplain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatComplainRepository extends JpaRepository<ChatComplain,Long> {
}
