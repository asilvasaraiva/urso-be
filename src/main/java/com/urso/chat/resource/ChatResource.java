package com.urso.chat.resource;

import com.urso.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chats")
public class ChatResource {

    private final ChatService chatService;

    @Autowired
    public ChatResource(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/")
    public ResponseEntity getChatList(){
        return ResponseEntity.ok(chatService.listChats());
    }
}
