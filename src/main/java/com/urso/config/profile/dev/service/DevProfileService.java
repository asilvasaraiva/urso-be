package com.urso.config.profile.dev.service;


import com.urso.admin.entity.AdminMessage;
import com.urso.admin.repository.AdminRepository;
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
    private AdminRepository adminRepository;




    private final  LocalDate dt = LocalDate.of(2010,10,10);



    public  void InstantiateDataBase(){

    // Generic Users
        User user1 = createUser();
        User user2 = createUser();
        User user3 = createUser();

        user2.setName("User 2");
        user3.setName("User 3");

        userRepository.saveAll(Arrays.asList(user1,user2,user3));


        UserReview userReview1 = createUserReview();
        UserReview userReview2 = createUserReview();
        UserReview userReview3 = createUserReview();

        userReview1.setUserSender(user1);
        userReview2.setUserSender(user2);
        userReview3.setUserSender(user3);

        userReview1.setIdPersonReceiver(user2.getIdUser());
        userReview2.setIdPersonReceiver(user1.getIdUser());
        userReview3.setIdPersonReceiver(user2.getIdUser());
        userReviewRepository.saveAll(Arrays.asList(userReview1,userReview2,userReview3));


        user1.addReview(userReview2);
        user2.addReview(userReview1);
        user3.addReview(userReview3);

    //Generic Chats and configurations

        Chat chat = createChat();
        Chat chat2 = createChat();
        Chat chat3 = createChat();
        Chat chat4 = createChat();

        chat.setIdChatOwner(user1.getIdUser());
        chat.addParticipants(user1);
        chat.addParticipants(user2);
        chat.addParticipants(user3);

        chat2.addParticipants(user1);
        chat3.addParticipants(user1);
        chat4.addParticipants(user2);
        chatRepository.saveAll(Arrays.asList(chat,chat2,chat3,chat4));

    //Generic Messages
        ChatMessage chatMessage1 = createChatMessage();
        ChatMessage chatMessage2 = createChatMessage();
        ChatMessage chatMessage3 = createChatMessage();
        ChatMessage chatMessage4 = createChatMessage();
        ChatMessage chatMessage5 = createChatMessage();
        ChatMessage chatMessage6 = createChatMessage();
        ChatMessage chatMessage7 = createChatMessage();

        chatMessage1.setChat(chat);
        chatMessage2.setChat(chat);
        chatMessage3.setChat(chat);

        chatMessage4.setChat(chat2);
        chatMessage5.setChat(chat3);

        chatMessage6.setChat(chat4);
        chatMessage7.setChat(chat4);

        chatMessage1.setIdUserSender(user1.getIdUser());
        chatMessage2.setIdUserSender(user1.getIdUser());
        chatMessage3.setIdUserSender(user1.getIdUser());

        chatMessage1.setIdUserSender(user2.getIdUser());
        chatMessage2.setIdUserSender(user3.getIdUser());

        chatMessage3.setIdUserSender(user3.getIdUser());
        chatMessage1.setIdUserSender(user3.getIdUser());
        chatMessage2.setIdUserSender(user2.getIdUser());
        chatMessageRepository.saveAll(Arrays.asList(chatMessage1,chatMessage2,chatMessage3,chatMessage4,chatMessage5,chatMessage6,chatMessage7));;


    //Setting messages to Chats
        chat.addMessage(chatMessage1);
        chat.addMessage(chatMessage2);

        chat2.addMessage(chatMessage3);
        chat2.addMessage(chatMessage7);
        chat3.addMessage(chatMessage4);

        chat4.addMessage(chatMessage5);
        chat4.addMessage(chatMessage6);
        chatRepository.saveAll(Arrays.asList(chat,chat2,chat3,chat4));

    //Adding chats to users
        user1.addChat(chat);
        user1.addChat(chat2);
        user1.addChat(chat3);
        user1.addChat(chat4);
        user2.addChat(chat);
        user3.addChat(chat);

        ChatComplain chatComplain = createChatComplain();

        chatComplain.setIdChat(chat.getIdChat());
        chatComplain.setIdUser(user1.getIdUser());

        chatComplainRepository.save(chatComplain);
        userRepository.saveAll(Arrays.asList(user1,user2,user3));


    //Admin Messages
        AdminMessage adminMessage = AdminMessage.builder().content("huehueheuheu").idUserSender(user1.getIdUser())
                .isRead(true)
                .build();

        adminRepository.save(adminMessage);


    //Queries in Repositories Uncomment below



//        List<Chat> c = chatRepository.findByParticipants(user1);
//        show(c);
//
//        List<ChatMessage> chatMessage =  chatMessageRepository.findByChat(chat);
//        show(chatMessage);
//
//        List<User> usr = userRepository.findByUserChats(chat2);
//        showName(usr);





    }


    public  <T> void show(List<T> list){
        for(T u : list){
            log.info(u.toString());
        }
    }

    public void showName(List<User> list){
        for(User u : list){
            log.info(u.getName());
        }
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
                .participants(new ArrayList<>())
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
