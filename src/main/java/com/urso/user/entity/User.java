package com.urso.user.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = -5586800209897275150L;

    private final long idUser;
    private final String name;
    private final String surname;
    private final String email;
    private final String password;
    private final LocalDateTime age;
    private final LocalDateTime joinDate;
    private final boolean isAdmin = false;
    private final List<UserReview> reviews;
}
