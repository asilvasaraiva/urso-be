package br.com.urso.chat.service;

import br.com.urso.chat.entity.Chat;
import br.com.urso.chat.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    private final ChatRepository chatRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }


    public List<Chat> listChats(){
        return chatRepository.findAll();
    }
}
