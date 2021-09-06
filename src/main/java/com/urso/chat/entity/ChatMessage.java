package com.urso.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@Table(name = "chat_message")
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage implements Serializable {
    private static final long serialVersionUID = 7954899713317230615L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_message_seq")
    @SequenceGenerator(name = "chat_message_seq", sequenceName = "seq_chat_message_", allocationSize = 1)
    @Column(name = "id_chat_message")
    private long idChatMessage;

    @Column(name = "id_user")
    private long idUser;

    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @Column(name = "dat_creation")
    private LocalDateTime createAt;

    @Column(name = "content")
    private String content;

}
