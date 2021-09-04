package com.urso.user.enitity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserReview implements Serializable {

    private static final long serialVersionUID = -3788024303917538267L;
    private final long idReview;
    private final long idPersonSender;
    private final long idPersonReceiver;
    private final boolean status;
    private final LocalDateTime createdAt;
    private final String content;
}
