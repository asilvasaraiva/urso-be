package com.urso.user.entity;

import com.urso.chat.entity.Chat;
import com.urso.utils.chatandusers.entity.ChatsAndUsers;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = -5586800209897275150L;


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "sq_user_id", allocationSize = 1)
    @Column(name = "id_user")
    private  long idUser;

    @Column(name = "user_name")
    private  String name;

    @Column(name = "user_surname")
    private  String surname;

    @Column(name = "user_email")
    private  String email;

    @Column(name = "password")
    private  String password;

    @Column(name = "age")
    private  LocalDate age;

    @Column(name = "join_date")
    private  LocalDate joinDate;

    @Column(name = "is_admin")
    private boolean isAdmin = false;

    @OneToMany(mappedBy = "userSender",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserReview> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<ChatsAndUsers> listOfChats= new ArrayList<>();


public boolean addReview(UserReview ur){
    if(ur.isAccepted()){
        this.reviews.add(ur);
        return true;
    }else{
        return false;
    }
}

public void addChat(ChatsAndUsers chatsAndUsers ){
    if(chatsAndUsers.getUser().equals(this.idUser))
    this.listOfChats.add(chatsAndUsers);
}

}
