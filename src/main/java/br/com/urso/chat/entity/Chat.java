package br.com.urso.chat.entity;

import br.com.urso.user.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "chat_title")
    private String chatTitle;

    @Column(name = "id_chat_owner")
    private long idChatOwner;

    @Column(name = "dat_creation")
    private LocalDateTime createAt = LocalDateTime.now();

    @JsonManagedReference
    @OneToMany(mappedBy = "chat")
    @ToString.Exclude
    private List<ChatMessage> messages= new ArrayList<>();

    @JsonBackReference
    @ManyToMany(mappedBy = "userChats")
    @ToString.Exclude
    private List<User> participants= new ArrayList<>();

    @Column(name = "max_participants")
    private Integer maxParticipants;

    @Column(name = "is_chat_active")
    private boolean isChatActive;

    public void addMessage(ChatMessage chatMessage){
        this.messages.add(chatMessage);
    }

    public boolean addParticipants(User user){
        if(this.participants.size()<maxParticipants) {
            this.participants.add(user);
            return true;
        }else{
            return false;
        }
    }

}
