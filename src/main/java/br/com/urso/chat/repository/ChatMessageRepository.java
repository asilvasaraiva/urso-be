package br.com.urso.chat.repository;

import br.com.urso.chat.entity.Chat;
import br.com.urso.chat.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {

    List<ChatMessage> findByChat(Chat c);

    List<ChatMessage> findByIdUserSender(Long id);

}
