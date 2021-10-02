package br.com.urso.chat.resource;

import br.com.urso.chat.entity.*;
import br.com.urso.chat.service.ChatService;
import br.com.urso.config.UserPrincipal;
import br.com.urso.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequestMapping("/chats")
public class ChatResource {

    private final ChatService chatService;

    @Autowired
    private  UserService userService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ChatResource(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/")
    public ResponseEntity getChatList(){
        return ResponseEntity.ok(chatService.listChats());
    }


    @MessageMapping("/chat.register")
    public void create(@Payload ChatStompMessage chatMessageRegister,
                              SimpMessageHeaderAccessor headerAccessor, @Header("simpUser") UserPrincipal principal) {
        log.info("|--- CHAT.register---|");
        log.info("|--- ID.register principal---|"+principal.getName());
        chatService.register(chatMessageRegister);
        chatMessageRegister.getListOfParticipants()
                .forEach(p->messagingTemplate.convertAndSendToUser(p.toString(), "/queue/messages", chatMessageRegister));

    }


    @MessageMapping("/chat.send")
    public void sendMessage(@Payload ChatStompMessage chatMessage , @Header("simpUser") UserPrincipal principal) {
        log.info("|--- CHAT.send ID ---|",chatMessage.getChatID());
        log.info("|--- CHAT.send PRINCIPAL ---|",principal.getName());

        if( chatMessage.getListOfParticipants().size()>1){
            chatMessage.getListOfParticipants().forEach(u->messagingTemplate.convertAndSendToUser(u.toString(), "/queue/messages", chatMessage));
        }else{
            messagingTemplate.convertAndSendToUser(principal.getName(),"queue/messages",chatMessage);
        }
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
