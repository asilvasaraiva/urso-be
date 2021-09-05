package com.urso.chat.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ChatComplain implements Serializable {
    private static final long serialVersionUID = -560374191929774750L;

    private final long idComplain;
    private final long idUser;
    private final long idChat;
    private final LocalDateTime createAt;
    private final String content;
}
