package com.urso.admin.entity;

import com.urso.user.entity.User;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@Table(name = "admin_message")
public class AdminMessage implements Serializable {
    private static final long serialVersionUID = 5456822156544118281L;

    @Id
    @Column(name = "id_message")
    private final long idMessage;

    @Column(name = "id_user_sender")
    private final long idUserSender;

    @Column(name = "is_read")
    private final boolean isRead = false;

    @Column(name = "dat_creation")
    private final LocalDateTime createAt;

    @Column(name = "content")
    private final String content;


}
