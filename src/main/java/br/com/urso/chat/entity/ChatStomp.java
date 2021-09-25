package br.com.urso.chat.entity;

import lombok.Data;

@Data
public class ChatStomp {
    private String content;
    private String name;
    private Long sender;
    private MessageType type;

    public enum MessageType {
        CHAT, LEAVE, JOIN
    }

}
