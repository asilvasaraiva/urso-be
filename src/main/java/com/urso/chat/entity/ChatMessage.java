package com.urso.chat.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ChatMessage implements Serializable {
    private static final long serialVersionUID = 7954899713317230615L;

    private final long idChatMessage;
    private final long idUser;
    private final long idChat;
    private final LocalDateTime createAt;
    private final String content;

}
