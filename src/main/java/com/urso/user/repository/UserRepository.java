package com.urso.user.repository;

import com.urso.chat.entity.Chat;
import com.urso.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Transactional
public interface UserRepository extends JpaRepository<User,Integer> {



}
