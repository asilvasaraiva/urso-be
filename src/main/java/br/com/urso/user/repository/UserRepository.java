package br.com.urso.user.repository;

import br.com.urso.chat.entity.Chat;
import br.com.urso.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    List<User> findByUserChats(Chat c);

    List<User> findByIsAdmin(Boolean isAdmin);

    User findByEmail(String email);

}
