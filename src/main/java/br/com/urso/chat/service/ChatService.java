package br.com.urso.chat.service;

import br.com.urso.chat.entity.*;
import br.com.urso.chat.exception.ChatNotFoundException;
import br.com.urso.chat.repository.ChatComplainRepository;
import br.com.urso.chat.repository.ChatMessageRepository;
import br.com.urso.chat.repository.ChatRepository;
import br.com.urso.user.entity.User;
import br.com.urso.user.repository.UserRepository;
import br.com.urso.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
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

@Transactional
    public ChatStompMessage register(ChatStompMessage chatStompMessage) {
        Long userId=Long.parseLong(chatStompMessage.getIdUser());
        User user = userService.getUserById(userId);

            Chat c = new Chat();
            c.setMaxParticipants(chatStompMessage.getMaxParticipants());
            c.setChatTitle(chatStompMessage.getChatTitle());
            c.setIdChatOwner(userId);
            c.setChatDescription(chatStompMessage.getChatDescription());
            c.setChatActive(true);
            c.addParticipants(user);
            user.addChat(c);
            chatRepository.save(c);
            chatStompMessage.setChatID(String.valueOf(c.getIdChat()));
            chatStompMessage.getListOfParticipants().add(user.getIdUser());

        chatStompMessage.setNameUser(user.getName());
        return chatStompMessage;
    }

    @Transactional
    public ChatStompMessage joinChat(ChatStompMessage chatStompMessage) {
        Long userId=Long.parseLong(chatStompMessage.getIdUser());
        User user = userService.getUserById(userId);

        if(chatStompMessage.getChatID()!=null){
            Long chatId=Long.parseLong(chatStompMessage.getChatID());
            Chat c = chatByID(chatId);
            if(c.getParticipants().contains(user)){
                chatStompMessage.setType(ChatStompMessage.MessageType.ALREADY_IN);
                chatStompMessage.setListOfParticipants(null);
                return chatStompMessage;
            }else if(c.addParticipants(user)){//testando sala cheia VALIDAÇÃO INCOMPLETA, POIS MOSTRA MENSAGEM NOS CHATS QUE O USUÁRIO ESTÁ
                c.getParticipants().stream().forEach(cp->chatStompMessage.getListOfParticipants().add(cp.getIdUser()));
                user.addChat(c);
                chatRepository.save(c);
                chatStompMessage.setChatID(chatStompMessage.getChatID());
            }else {
                chatStompMessage.setType(ChatStompMessage.MessageType.FULL);
                chatStompMessage.setListOfParticipants(null);
            }
        }
        chatStompMessage.setNameUser(user.getName());
        return chatStompMessage;
    }

    @Transactional
    public ChatStompMessage saveMessage(ChatStompMessage chatStompMessage) {
        Long userId=Long.parseLong(chatStompMessage.getIdUser());
        Long chatId=Long.parseLong(chatStompMessage.getChatID());

        Chat chat = chatByID(chatId);
        User u = userService.getUserById(userId);

        ArrayList<Long> listaParticipantes = (ArrayList<Long>) chat.getParticipants().stream().map(p->p.getIdUser()).collect(Collectors.toList());
        chatStompMessage.setListOfParticipants(listaParticipantes);
        ChatMessage c = new ChatMessage();
        c.setChat(chat);
        c.setIdUserSender(u.getIdUser());
        c.setContent(chatStompMessage.getContent());

        chatMessageRepository.save(c);
        return chatStompMessage;
    }



}
