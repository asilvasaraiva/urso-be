package br.com.urso.chat.resource;

import br.com.urso.chat.entity.ChatComplain;
import br.com.urso.chat.entity.ChatMessage;
import br.com.urso.chat.entity.ChatStomp;
import br.com.urso.chat.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;


@RestController
@Slf4j
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


    @MessageMapping("/chat.register")
    @SendTo("/topic/public")
    public ChatStomp register(@Payload ChatStomp chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        log.info("|--- CHAT REGISTER---|");
        return chatService.register(headerAccessor,chatMessage);
    }

    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public ChatStomp sendMessage(@Payload ChatStomp chatMessage) {
        log.info("|--- CHAT SEND---|");
       return chatService.addUser(chatMessage);
    }


    public void createChat(){}
    public void findChatById(){}

    @PostMapping("/complains/from/{idUser}/tochat/{idChat}")
    public ResponseEntity chatComplain(@PathVariable("idUser")Long idUser,
                                       @PathVariable("idChat") Long idChat,
                                       @RequestBody ChatComplain c){
        return ResponseEntity.ok(chatService.createComplain(c,idUser,idChat));
    }

    public void sendMessageChat(){}
    public void findMessage(){}

}
