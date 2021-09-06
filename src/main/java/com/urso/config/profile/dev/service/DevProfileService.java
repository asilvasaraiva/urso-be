package com.urso.config.profile.dev.service;


import com.urso.chat.entity.Chat;
import com.urso.chat.entity.ChatComplain;
import com.urso.chat.entity.ChatMessage;
import com.urso.chat.repository.ChatRepository;
import com.urso.user.entity.User;
import com.urso.user.entity.UserReview;
import com.urso.user.repository.UserRepository;
import com.urso.utils.DataUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
@Slf4j
public class DevProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRepository chatRepository;


    private final  LocalDate dt = LocalDate.of(2010,10,10);



    public  void InstantiateDataBase(){


    }


    private User createUser(){
        return  User.builder()
                .idUser(123L)
                .age(dt)
                .email("teste@teste.com")
                .joinDate(dt)
                .name("Teste 1")
                .surname("Lorem")
                .password("asdqwe")
                .build();
    }

    private UserReview createUserReview(){
        Random aleatorio = new Random();
        long id = aleatorio.nextLong();
        return UserReview.builder()
                .idReview(id)
                .userSender(null)
                .content("teste de depoimento")
                .createdAt(dt.atTime(12,25))
                .idPersonReceiver(321L)
                .build();
    }

    private Chat createChat(){
        return  Chat.builder()
                .idChat(12L)
                .createAt(dt.atTime(00,00))
                .idChatOwner(123L)
                .isChatActive(true)
                .maxParticipants(2)
                .build();
    }

    private ChatComplain createChatComplain(){
        return ChatComplain.builder()
                .idComplain(8888L)
                .content("Ameaça")
                .idUser(123L)
                .build();
    }

    private List<UserReview> geraListaReview(){

        List<UserReview> list = new ArrayList<>();
        list.add(createUserReview());
        list.add(createUserReview());
        list.add(createUserReview());

        return list;
    }



    public void teste(){
        log.info("|----- Utilizando Profile de Teste -----|");
    }

}
