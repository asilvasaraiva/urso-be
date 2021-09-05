package com.urso.chat.entity;

import com.urso.user.entity.User;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Chat implements Serializable {
    private static final long serialVersionUID = -8672516887125802883L;

    private final long idChat;
    private final long idChatOwner;
    private final LocalDateTime createAt;
    private final List<ChatMessage> Messages;
    private final List<User> participants;
    private final Integer maxParticipants;

}
