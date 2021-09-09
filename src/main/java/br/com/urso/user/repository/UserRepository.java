package br.com.urso.user.repository;

import br.com.urso.chat.entity.Chat;
import br.com.urso.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    List<User> findByUserChats(Chat c);

}
