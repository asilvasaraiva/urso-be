package com.urso.chat.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@Table(name = "chat_complain")
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
    private final LocalDateTime createAt;

    @Column(name = "content")
    private final String content;
}
