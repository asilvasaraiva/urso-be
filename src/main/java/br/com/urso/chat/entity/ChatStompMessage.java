package br.com.urso.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatStompMessage {
    private String content;
    private String chatTitle;
    private String idUser;
    private String nameUser;
    private String chatID;
    private int maxParticipants;
    private ArrayList<Long> listOfParticipants= new ArrayList<>();
    private MessageType type;

    public enum MessageType {
        CHAT, LEAVE, JOIN, FULL
    }

}
