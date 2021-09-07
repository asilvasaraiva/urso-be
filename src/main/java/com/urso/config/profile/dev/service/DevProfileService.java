package com.urso.config.profile.dev.service;


import com.urso.chat.entity.Chat;
import com.urso.chat.entity.ChatComplain;
import com.urso.chat.entity.ChatMessage;
import com.urso.chat.repository.ChatComplainRepository;
import com.urso.chat.repository.ChatMessageRepository;
import com.urso.chat.repository.ChatRepository;
import com.urso.user.entity.User;
import com.urso.user.entity.UserReview;
import com.urso.user.repository.UserRepository;
import com.urso.user.repository.UserReviewRepository;
import com.urso.utils.DataUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;


@Service
@Slf4j
public class DevProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserReviewRepository userReviewRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ChatComplainRepository chatComplainRepository;




    private final  LocalDate dt = LocalDate.of(2010,10,10);



    public  void InstantiateDataBase(){




        User user1 = createUser();
        User user2 = createUser();
        User user3 = createUser();

        user2.setName("User 2");
        user3.setName("User 3");
        userRepository.saveAll(Arrays.asList(user1,user2,user3));

        UserReview userReview1 = createUserReview();
        UserReview userReview2 = createUserReview();
//        UserReview userReview3 = createUserReview();

        userReview1.setUserSender(user1);
        userReview2.setUserSender(user2);
//        userReview3.setUserSender(user3);

        userReview1.setIdPersonReceiver(user2.getIdUser());
        userReview2.setIdPersonReceiver(user1.getIdUser());
//        userReview3.setIdPersonReceiver(user2.getIdUser());

        userReviewRepository.save(userReview1);
        userReviewRepository.save(userReview2);
//        userReviewRepository.save(userReview3);

        user1.addReview(userReview2);
        user2.addReview(userReview1);
//        user3.addReview(userReview3);

        Chat chat = createChat();

        chat.setIdChatOwner(user1.getIdUser());
        chat.addParticipants(user1);
        chat.addParticipants(user2);
        //chat.addParticipants(user3);


        chatRepository.save(chat);

        ChatMessage chatMessage1 = createChatMessage();
        ChatMessage chatMessage2 = createChatMessage();
        ChatMessage chatMessage3 = createChatMessage();

        chatMessage1.setChat(chat);
        chatMessage2.setChat(chat);
        chatMessage3.setChat(chat);

        chatMessage1.setIdUser(user1.getIdUser());
        chatMessage2.setIdUser(user1.getIdUser());
        chatMessage3.setIdUser(user1.getIdUser());


        chatMessageRepository.save(chatMessage1);
        chatMessageRepository.save(chatMessage2);
        chatMessageRepository.save(chatMessage3);


        chat.addMessage(chatMessage1);
//        chat.addMessage(chatMessage2);
        chat.addMessage(chatMessage3);

        //chat.setMessages(messages);

        chatRepository.save(chat);
        user1.addChat(chat);
        user2.addChat(chat);

        ChatComplain chatComplain = createChatComplain();
//        chatComplain.setIdChat(chat.getIdChat());
//
//        chatComplainRepository.save(chatComplain);
        userRepository.saveAll(Arrays.asList(user1,user2,user3));


    }


    private User createUser(){
        return  User.builder()

                .age(dt)
                .email("teste@teste.com")
                .joinDate(dt)
                .name("Teste 1")
                .surname("Lorem")
                .password("asdqwe")
                .userChats(new ArrayList<>())
                .reviews(new ArrayList<UserReview>())
                .build();
    }

    private UserReview createUserReview(){
        return UserReview.builder()
                .userSender(createUser())
                .isAccepted(true)
                .content("teste de depoimento")
                .createdAt(dt.atTime(12,25))
                .build();
    }

    private List<UserReview> geraListaReview(){

        List<UserReview> list = new ArrayList<>();
        list.add(createUserReview());
//        list.add(createUserReview());
//        list.add(createUserReview());

        return list;
    }

    private Chat createChat(){
        return  Chat.builder()
                .createAt(dt.atTime(00,00))
                .participants(new HashSet<User>())
                .messages(new ArrayList<>())
                .isChatActive(true)
                .maxParticipants(3)
                .build();
    }

    private ChatMessage createChatMessage(){
        Random aleatorio = new Random(10);
        long id = Math.abs(aleatorio.nextInt());
        return ChatMessage.builder()
                .createAt(LocalDate.now().atTime(23,45))
                .content("message "+id)
                .build();
    }

    private ChatComplain createChatComplain(){
        return ChatComplain.builder()
                .content("Ameaça")
                .idUser(123L)
                .build();
    }

    private Set<ChatMessage> geraChatMessages(){
        Set<ChatMessage> list = new HashSet<>();
        list.add(createChatMessage());
        list.add(createChatMessage());
        list.add(createChatMessage());

        return list;
    }



    public void teste(){
        log.info("|----- Utilizando Profile de Teste -----|");
    }

}
