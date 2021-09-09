package br.com.urso.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
@AllArgsConstructor
public class AdminMessage implements Serializable {
    private static final long serialVersionUID = 5456822156544118281L;

    @Id
    @Column(name = "id_message")
    private long idMessage;

    @Column(name = "id_user_sender")
    private long idUserSender;

    @Column(name = "is_read")
    private boolean isRead = false;

    @Column(name = "dat_creation")
    private LocalDateTime createAt;

    @Column(name = "content")
    private String content;


}
