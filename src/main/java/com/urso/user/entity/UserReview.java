package com.urso.user.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user_review")
public class UserReview implements Serializable {

    private static final long serialVersionUID = -3788024303917538267L;

    @Id
    @Column(name = "id_review")
    private final long idReview;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private final User userSender;

    @Column(name = "id_person_receiver")
    private final long idPersonReceiver;

    @Column(name = "status")
    private final boolean isAccepted = false;

    @Column(name = "create_at")
    private final LocalDateTime createdAt;

    @Column(name = "content")
    private final String content;

}
