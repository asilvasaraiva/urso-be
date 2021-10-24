package br.com.urso.admin.entity;

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
@Table(name = "admin_message")
@NoArgsConstructor
@AllArgsConstructor
public class AdminMessage implements Serializable {
    private static final long serialVersionUID = 5456822156544118281L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adm_msg")
    @SequenceGenerator(name = "adm_msg", sequenceName = "adm_msg_id", allocationSize = 1)
    @Column(name = "id_message")
    private long idMessage;

    @Column(name = "id_user_sender")
    private long idUserSender;

    @Column(name = "is_read")
    private boolean isRead = false;

    @Column(name = "dat_creation")
    private LocalDateTime createAt = LocalDateTime.now();

    @Column(name = "content")
    private String content;

    @Column(name = "type_of")
    private String typeOf;

    @Column(name = "title")
    private String title;


}
