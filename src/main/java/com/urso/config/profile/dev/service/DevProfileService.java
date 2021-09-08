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
import com.urso.utils.chatandusers.entity.ChatsAndUsers;
import com.urso.utils.chatandusers.repository.ChatAndUseRepository;
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

    @Autowired
    private ChatAndUseRepository chatAndUseRepository;




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


        userReview1.setUserSender(user1);
        userReview2.setUserSender(user2);


        userReview1.setIdPersonReceiver(user2.getIdUser());
        userReview2.setIdPersonReceiver(user1.getIdUser());


        userReviewRepository.save(userReview1);
        userReviewRepository.save(userReview2);

        user1.addReview(userReview2);
        user2.addReview(userReview1);


        Chat chat = createChat();

        chat.setIdChatOwner(user1.getIdUser());


        ChatsAndUsers chatsAndUsers1 = ChatsAndUsers.builder().user(user1).chat(chat).build();
        ChatsAndUsers chatsAndUsers2 = ChatsAndUsers.builder().user(user2).chat(chat).build();
        ChatsAndUsers chatsAndUsers3 = ChatsAndUsers.builder().user(user3).chat(chat).build();




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

        chatAndUseRepository.save(chatsAndUsers1);
        chatAndUseRepository.save(chatsAndUsers2);
        chatAndUseRepository.save(chatsAndUsers3);


        chat.addParticipants(chatsAndUsers1);
        chat.addParticipants(chatsAndUsers2);
        chat.addParticipants(chatsAndUsers3);


        user1.addChat(chatsAndUsers1);
        user2.addChat(chatsAndUsers2);

        ChatComplain chatComplain = createChatComplain();

        chatComplain.setIdChat(chat.getIdChat());

        chatComplainRepository.save(chatComplain);

        chatRepository.save(chat);
        userRepository.saveAll(Arrays.asList(user1,user2,user3));


        User userByUser = chatAndUseRepository.findByUser(user1);
       // User userById = chatAndUseRepository.findUserById(user1.getIdUser());

//        Chat chatByChat = chatAndUseRepository.findByChat(chat);
//        Chat chatById = chatAndUseRepository.findChatById(chat.getIdChat());

        log.info("userByUser: "+userByUser.getName());
       // log.info("userById: "+userById.getName());
//        log.info("chatByChat: "+chatByChat.getIdChat());
//        log.info("chatById: "+chatById.getIdChat());


    }


    private User createUser(){
        return  User.builder()

                .age(dt)
                .email("teste@teste.com")
                .joinDate(dt)
                .name("Teste 1")
                .surname("Lorem")
                .password("asdqwe")
                .listOfChats(new ArrayList<>())
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
                .participants(Collections.singletonList(new ChatsAndUsers()))
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
