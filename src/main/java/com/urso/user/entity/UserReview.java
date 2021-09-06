package com.urso.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@Table(name = "user_review")
@NoArgsConstructor
@AllArgsConstructor
public class UserReview implements Serializable {

    private static final long serialVersionUID = -3788024303917538267L;

    @Id
    @Column(name = "id_review")
    private long idReview;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User userSender;

    @Column(name = "id_person_receiver")
    private long idPersonReceiver;

    @Column(name = "status")
    private boolean isAccepted = false;

    @Column(name = "create_at")
    private LocalDateTime createdAt;

    @Column(name = "content")
    private String content;

}
