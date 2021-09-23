package br.com.urso.user.vo;

import br.com.urso.user.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReviewVO {
    private long idReview;

//    private long userSender;
//
//    private long userReceiver;

    private boolean isAccepted ;

    private boolean isVisualized ;

    private LocalDateTime createdAt;

    private String content;

}
