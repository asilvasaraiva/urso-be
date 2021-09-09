package br.com.urso.chat.repository;

import br.com.urso.chat.entity.Chat;
import br.com.urso.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat,Long> {
    List<Chat> findByParticipants(User user);

    
}
