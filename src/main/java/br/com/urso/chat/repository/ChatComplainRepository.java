package br.com.urso.chat.repository;

import br.com.urso.chat.entity.ChatComplain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatComplainRepository extends JpaRepository<ChatComplain,Long> {
}
