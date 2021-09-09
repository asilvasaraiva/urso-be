package br.com.urso.user.repository;

import br.com.urso.user.entity.UserReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReviewRepository extends JpaRepository<UserReview,Integer> {
}
