package br.com.urso.user.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import br.com.urso.chat.entity.Chat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = -5586800209897275150L;


    @Id
    @ApiModelProperty(value = "Código do usuário")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "sq_user_id", allocationSize = 1)
    @Column(name = "id_user")
    private  long idUser;

    @ApiModelProperty(value = "Nome do usuário")
    @Column(name = "user_name")
    private  String name;

    @ApiModelProperty(value = "Sobrenome do usuário")
    @Column(name = "user_surname")
    private  String surname;

    @ApiModelProperty(value = "Email do usuário")
    @Column(name = "user_email")
    private  String email;

    @ApiModelProperty(value = "Senha do usuário")
    @Column(name = "password")
    private  String password;

    @ApiModelProperty(value = "Data de nascimento do usuário")
    @Column(name = "age")
    private  LocalDate age;

    @ApiModelProperty(value = "Data de entrada do usuário no sistema")
    @Column(name = "join_date")
    private  LocalDate joinDate;

    @ApiModelProperty(value = "Especifica se é um administrador")
    @Column(name = "is_admin")
    private boolean isAdmin = false;

    @ApiModelProperty(value = "Lista de depoimentos do usuário")
    @JsonManagedReference
    @OneToMany(mappedBy = "userSender",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserReview> reviews = new ArrayList<>();

    @ApiModelProperty(value = "Lista de chats que o usuário participa")
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
