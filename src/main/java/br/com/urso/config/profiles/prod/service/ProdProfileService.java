package br.com.urso.config.profiles.prod.service;


import br.com.urso.admin.entity.AdminMessage;
import br.com.urso.admin.repository.AdminRepository;
import br.com.urso.chat.entity.Chat;
import br.com.urso.chat.entity.ChatComplain;
import br.com.urso.chat.entity.ChatMessage;
import br.com.urso.chat.repository.ChatComplainRepository;
import br.com.urso.chat.repository.ChatMessageRepository;
import br.com.urso.chat.repository.ChatRepository;
import br.com.urso.security.oauth2.entity.AuthProvider;
import br.com.urso.user.entity.User;
import br.com.urso.user.entity.UserReview;
import br.com.urso.user.repository.UserRepository;
import br.com.urso.user.repository.UserReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;


@Service
@Slf4j
public class ProdProfileService {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserRepository userRepository;

    public void InstantiateDataBase() {
        User user1 = createUser(new User());
        userRepository.save(user1);
        log.info("|----- Admin em PROD inserido com sucesso-----|");
    }

    private User createUser(User u) {
        u.setBirth(LocalDate.now());
        u.setEmail("teste@teste.com");
        u.setJoinDate(LocalDate.now());
        u.setName("Admin 1");
        u.setSurname("Original");
        u.setAdmin(true);
        u.setPassword(encoder.encode("@1234"));
        u.setProvider(AuthProvider.local);
        return u;

    }
}
