package com.urso.admin.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AdminMessage implements Serializable {
    private static final long serialVersionUID = 5456822156544118281L;

    private final long idAdminMessage;
    private final long idUser;
    private final boolean status = false;
    private final LocalDateTime createAt;
    private final String content;


}
