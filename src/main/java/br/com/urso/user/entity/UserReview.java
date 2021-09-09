package br.com.urso.user.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_review_seq")
    @SequenceGenerator(name = "user_review_seq", sequenceName = "seq_user_review_", allocationSize = 1)
    @Column(name = "id_review")
    private long idReview;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "id_user_sender", nullable = false)
    @JsonBackReference
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
