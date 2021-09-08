package com.urso.chat.service;

import com.urso.chat.entity.Chat;
import com.urso.chat.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

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
