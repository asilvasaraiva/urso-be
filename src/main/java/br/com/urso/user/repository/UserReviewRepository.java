package br.com.urso.user.repository;

import br.com.urso.user.entity.User;
import br.com.urso.user.entity.UserReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserReviewRepository extends JpaRepository<UserReview,Long> {

    List<UserReview> findByUserSender(Long id);
    List<UserReview> findByUserReceiver(Long id);

}
