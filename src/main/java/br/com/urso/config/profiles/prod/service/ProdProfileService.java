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
import br.com.urso.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
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

    @Autowired
    UserService us;

    @PostConstruct
    public void InstantiateDataBase() {
        User user = createUser(new User());
        if(!userRepository.findByEmail(user.getEmail()).isPresent()) {
            userRepository.save(user);
            log.info("|----- Admin em PROD inserido com sucesso-----|");
        }else{
            log.info("|----- Admin em PROD existente-----|");
        }
    }


    private User createUser(User u) {
        u.setBirth(LocalDate.now());
        u.setEmail("teste@teste.com");
        u.setJoinDate(LocalDate.parse("2022-10-10"));
        u.setName("Admin 1");
        u.setSurname("Original");
        u.setAdmin(true);
        u.setPassword(encoder.encode("1234"));
        u.setProvider(AuthProvider.local);
        return u;

    }
}
