package com.urso.chat.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "chat_message")
public class ChatMessage implements Serializable {
    private static final long serialVersionUID = 7954899713317230615L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_message_seq")
    @SequenceGenerator(name = "chat_message_seq", sequenceName = "seq_chat_message_", allocationSize = 1)
    @Column(name = "id_chat_message")
    private final long idChatMessage;

    @Column(name = "id_user")
    private final long idUser;

    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    private final Chat chat;

    @Column(name = "dat_creation")
    private final LocalDateTime createAt;

    @Column(name = "content")
    private final String content;

}
