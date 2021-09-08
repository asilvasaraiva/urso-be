package com.urso.user.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.urso.chat.entity.Chat;
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

    @JsonManagedReference
    @OneToMany(mappedBy = "userSender",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserReview> reviews = new ArrayList<>();

    @ManyToMany
    @JsonManagedReference
    @JoinTable(
            name = "user_chat",
            joinColumns= @JoinColumn(name = "user_id",referencedColumnName = "id_user"),
            inverseJoinColumns =  @JoinColumn(name = "chat_id",referencedColumnName = "id_chat")
    )
    private List<Chat> userChats= new ArrayList<>();

public boolean addReview(UserReview ur){
    if(ur.isAccepted()){
        this.reviews.add(ur);
        return true;
    }else{
        return false;
    }
}

public void addChat(Chat c){
    this.userChats.add(c);
}

}
