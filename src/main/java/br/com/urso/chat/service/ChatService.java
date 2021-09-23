package br.com.urso.chat.service;

import br.com.urso.chat.entity.Chat;
import br.com.urso.chat.entity.ChatComplain;
import br.com.urso.chat.repository.ChatComplainRepository;
import br.com.urso.chat.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    private final ChatRepository chatRepository;

    private final ChatComplainRepository chatComplainRepository;



    @Autowired
    public ChatService(ChatRepository chatRepository, ChatComplainRepository chatComplainRepository) {
        this.chatRepository = chatRepository;
        this.chatComplainRepository = chatComplainRepository;
    }


    public List<Chat> listChats(){
        return chatRepository.findAll();
    }

    public ChatComplain createComplain(ChatComplain c,Long idUser, Long idChat){
        c.setIdUser(idUser);
        c.setIdChat(idChat);
        return chatComplainRepository.save(c);
    }
}
