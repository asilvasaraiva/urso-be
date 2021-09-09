package br.com.urso.chat.entity;

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
@Table(name = "chat_complain")
@NoArgsConstructor
@AllArgsConstructor
public class ChatComplain implements Serializable {
    private static final long serialVersionUID = -560374191929774750L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_complain_seq")
    @SequenceGenerator(name = "chat_complain_seq", sequenceName = "seq_chat_complain_", allocationSize = 1)
    @Column(name = "id_chat_complain")
    private long idComplain;

    @Column(name = "id_user")
    private long idUser;

    @Column(name = "id_chat")
    private long idChat;

    @Column(name = "dat_creation")
    private LocalDateTime createAt;

    @Column(name = "content")
    private String content;
}
