package br.com.urso.chat.resource;

import br.com.urso.chat.entity.ChatComplain;
import br.com.urso.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
