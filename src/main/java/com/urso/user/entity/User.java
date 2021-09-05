package com.urso.user.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = -5586800209897275150L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "sq_user_id", allocationSize = 1)
    @Column(name = "id_user")
    private final long idUser;

    @Column(name = "user_name")
    private final String name;

    @Column(name = "user_surname")
    private final String surname;

    @Column(name = "user_email")
    private final String email;

    @Column(name = "password")
    private final String password;

    @Column(name = "age")
    private final LocalDateTime age;

    @Column(name = "join_date")
    private final LocalDateTime joinDate;

    @Column(name = "is_admin")
    private final boolean isAdmin = false;


    @OneToMany(mappedBy = "userSender")
    private final List<UserReview> reviews = new ArrayList<>();
}
