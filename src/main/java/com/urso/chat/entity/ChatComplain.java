package com.urso.chat.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ChatComplain implements Serializable {
    private static final long serialVersionUID = -560374191929774750L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_complain_seq")
    @SequenceGenerator(name = "chat_complain_seq", sequenceName = "seq_chat_complain_", allocationSize = 1)
    @Column(name = "id_chat_complain")
    private final long idComplain;

    @Column(name = "id_user")
    private final long idUser;

    @Column(name = "id_chat")
    private final long idChat;

    @Column(name = "dat_creation")
    @Temporal(TemporalType.TIMESTAMP)
    private final LocalDateTime createAt;

    @Column(name = "content")
    private final String content;
}
