package br.com.urso.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import br.com.urso.chat.entity.Chat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    @NotEmpty(message = "Nome não pode ser vazio")
    private  String name;

    @ApiModelProperty(value = "Sobrenome do usuário")
    @Column(name = "user_surname")
    @NotEmpty(message = "Sobrenome não pode ser vazio")
    private  String surname;

    @ApiModelProperty(value = "Email do usuário")
    @Column(name = "user_email")
    @NotEmpty(message = "Email não pode ser vazio")
    @Email(message = "Email deve ser válido")
    private  String email;

    @ApiModelProperty(value = "Senha do usuário")
    @Column(name = "password")
    @NotEmpty(message = "Senha não pode ser vazia")
    private  String password;

    @ApiModelProperty(value = "Data de nascimento do usuário")
    @Column(name = "age")
    @NotNull(message = "Idade não pode ser vazio")
    private  LocalDate age;

    @ApiModelProperty(value = "Data de entrada do usuário no sistema")
    @Column(name = "join_date")
    private  LocalDate joinDate = LocalDate.now();

    @ApiModelProperty(value = "Especifica se é um administrador")
    @Column(name = "is_admin")
    private boolean isAdmin = false;

    @ApiModelProperty(value = "Lista de depoimentos do usuário")
    @OneToMany(mappedBy = "userSender",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserReview> reviews = new ArrayList<>();

    @ApiModelProperty(value = "Lista de chats que o usuário participa")
    @ManyToMany
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

public User merge(UserVO newUser ){

    if(newUser.getName()!=null){
        this.setName(newUser.getName());
    }

    if(newUser.getSurname()!=null){
        this.setSurname(newUser.getSurname());
    }

    if(newUser.getAge()!=null){
        this.setAge(LocalDate.parse( newUser.getAge()));
    }

    if(newUser.getEmail()!=null){
        this.setEmail(newUser.getEmail());
    }
    return this;
}
}
