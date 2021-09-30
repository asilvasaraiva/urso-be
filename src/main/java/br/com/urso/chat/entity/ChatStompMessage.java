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
    private String name;
    private String sender;
    private long chatID;
    private int maxParticipants;
    private ArrayList<String> listOfParticipants= new ArrayList<>();
    private MessageType type;

    public enum MessageType {
        CHAT, LEAVE, JOIN
    }

}
