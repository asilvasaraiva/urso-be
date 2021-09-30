package br.com.urso.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatStompMessage {
    private String content;
    private String name;
    private Long sender;
    private long chatID;
    private int maxParticipants;
    private MessageType type;

    public enum MessageType {
        CHAT, LEAVE, JOIN
    }

}