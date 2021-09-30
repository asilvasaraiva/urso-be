package br.com.urso.chat.service;

import br.com.urso.chat.entity.*;
import br.com.urso.chat.exception.ChatNotFoundException;
import br.com.urso.chat.repository.ChatComplainRepository;
import br.com.urso.chat.repository.ChatMessageRepository;
import br.com.urso.chat.repository.ChatRepository;
import br.com.urso.user.entity.User;
import br.com.urso.user.repository.UserRepository;
import br.com.urso.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

    private final ChatRepository chatRepository;

    private final ChatComplainRepository chatComplainRepository;

    private final ChatMessageRepository chatMessageRepository;

    private final UserService userService;

    private final UserRepository userRepository;



    @Autowired
    public ChatService(ChatRepository chatRepository, ChatComplainRepository chatComplainRepository, ChatMessageRepository chatMessageRepository, UserService userService, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.chatComplainRepository = chatComplainRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }


    @Transactional(readOnly = true)
    public Chat chatByID(Long id){
        Optional<Chat> c = chatRepository.findById(id);
        return c.orElseThrow(()-> new ChatNotFoundException("Chat: "+id +" not found in database"));
    }

    public List<Chat> listChats(){
        return chatRepository.findAll();
    }

    public ChatComplain createComplain(ChatComplain c,Long idUser, Long idChat){
        c.setIdUser(idUser);
        c.setIdChat(idChat);
        return chatComplainRepository.save(c);
    }

    //------CHAT CONFIGS-------//

    public ChatStompRegistry register(ChatStompRegistry chatStompRegistry) {
        User user = userService.getUserById(chatStompRegistry.getSender());

        if(chatStompRegistry.getChatID()>0){
            Chat c = chatByID(chatStompRegistry.getChatID());
            List<User> us = userService.usersFromChat(c);
            us.add(user);
            c.setParticipants(us);
            chatRepository.save(c);
            user.addChat(c);
            userRepository.save(user);
            chatStompRegistry.setChatID(chatStompRegistry.getChatID());
        }else{
            Chat c = new Chat();
            c.setMaxParticipants(3);
            c.setParticipants(Arrays.asList(user));
            chatRepository.save(c);
            user.addChat(c);
            userRepository.save(user);
            chatStompRegistry.setChatID(c.getIdChat());
        }
        chatStompRegistry.setName(user.getName());
        return chatStompRegistry;
    }

    public ChatStompMessage send(ChatStompMessage chatStompMessage){

        return chatStompMessage;
    }


    public ChatStompRegistry saveMessage(ChatStompRegistry chatStompRegistry) {
        User u = userService.getUserById(chatStompRegistry.getSender());

        ChatMessage c = ChatMessage.builder()
                .content(chatStompRegistry.getContent())
                .idUserSender(chatStompRegistry.getSender())
                .chat(chatRepository.getById(1L))
                .createAt(LocalDateTime.now()).build();

        chatMessageRepository.save(c);
        chatStompRegistry.setName(u.getName());
        return chatStompRegistry;
    }



}
