package com.urso.user.repository;

import com.urso.chat.entity.Chat;
import com.urso.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    List<User> findByUserChats(Chat c);

}
