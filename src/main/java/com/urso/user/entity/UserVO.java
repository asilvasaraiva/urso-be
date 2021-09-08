package com.urso.user.entity;


import com.urso.chat.entity.Chat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserVO {

    private long idUser;

    private String name;

    private String surname;

    private String email;

    private String password;

    private LocalDate age;

    private LocalDate joinDate;

    private boolean isAdmin = false;

    private List<UserReview> reviews = new ArrayList<>();

}
