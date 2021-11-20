package br.com.urso.user.repository;

import br.com.urso.chat.entity.Chat;
import br.com.urso.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    List<User> findByUserChats(Chat c);

    List<User> findByIsAdmin(Boolean isAdmin);

    Optional<User> findByEmail(String email);

}
