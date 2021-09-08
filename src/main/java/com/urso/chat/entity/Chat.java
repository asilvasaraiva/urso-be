package com.urso.chat.entity;

import com.urso.user.entity.User;
import com.urso.utils.chatandusers.entity.ChatsAndUsers;
import lombok.*;

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
@AllArgsConstructor
@NoArgsConstructor
public class Chat implements Serializable {
    private static final long serialVersionUID = -8672516887125802883L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_seq")
    @SequenceGenerator(name = "chat_seq", sequenceName = "sq_chat_id", allocationSize = 1)
    @Column(name = "id_chat")
    private long idChat;

    @Column(name = "id_chat_owner")
    private long idChatOwner;

    @Column(name = "dat_creation")
    private LocalDateTime createAt;

    @OneToMany(mappedBy = "chat")
    private List<ChatMessage> messages= new ArrayList<>();


//    @ManyToMany(mappedBy = "userChats")
//    private Set<User> participants= new HashSet<>();

    @OneToMany(mappedBy = "chat",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChatsAndUsers> participants= new ArrayList<>();


    @Column(name = "max_participants")
    private Integer maxParticipants;

    @Column(name = "is_chat_active")
    private boolean isChatActive;

    public void addMessage(ChatMessage chatMessage){
        this.messages.add(chatMessage);
    }

    public boolean addParticipants(ChatsAndUsers chatsAndUsers){
        if(this.participants.size()<maxParticipants && chatsAndUsers.getChat().equals(this.idChat)){
            this.participants.add(chatsAndUsers);
            return true;
        }else{
            return false;
        }
    }

}
