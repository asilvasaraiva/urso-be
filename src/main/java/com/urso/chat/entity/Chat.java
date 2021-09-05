package com.urso.chat.entity;

import com.urso.user.entity.User;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Builder
@Table(name = "chat")
public class Chat implements Serializable {
    private static final long serialVersionUID = -8672516887125802883L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_seq")
    @SequenceGenerator(name = "chat_seq", sequenceName = "sq_chat_id", allocationSize = 1)
    @Column(name = "id_chat")
    private final long idChat;

    @Column(name = "id_chat_owner")
    private final long idChatOwner;

    @Column(name = "dat_creation")
    private final LocalDateTime createAt;

    @OneToMany(mappedBy = "chat")
    private final Set<ChatMessage> messages= new HashSet<>();

    @ManyToMany(mappedBy = "userChats")
    private final Set<User> participants= new HashSet<>();

    @Column(name = "max_participants")
    private final Integer maxParticipants;

    @Column(name = "is_chat_active")
    private final boolean isChatActive;

}
