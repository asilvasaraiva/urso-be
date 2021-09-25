package br.com.urso.chat.service;

import br.com.urso.chat.entity.Chat;
import br.com.urso.chat.entity.ChatComplain;
import br.com.urso.chat.entity.ChatMessage;
import br.com.urso.chat.entity.ChatStomp;
import br.com.urso.chat.repository.ChatComplainRepository;
import br.com.urso.chat.repository.ChatRepository;
import br.com.urso.user.entity.User;
import br.com.urso.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    private final ChatRepository chatRepository;

    private final ChatComplainRepository chatComplainRepository;

    private final UserService userService;



    @Autowired
    public ChatService(ChatRepository chatRepository, ChatComplainRepository chatComplainRepository, UserService userService) {
        this.chatRepository = chatRepository;
        this.chatComplainRepository = chatComplainRepository;
        this.userService = userService;
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

    public ChatStomp register(SimpMessageHeaderAccessor headerAccessor, ChatStomp chatStomp) {
        User u = userService.getUserById(chatStomp.getSender());
        chatStomp.setName(u.getName());
        headerAccessor.getSessionAttributes().put(u.getName(), chatStomp.getSender());
        return chatStomp ;
    }

    public ChatStomp addUser(ChatStomp chatStomp) {
        User u = userService.getUserById(chatStomp.getSender());
        chatStomp.setName(u.getName());
        return chatStomp ;
    }
}
