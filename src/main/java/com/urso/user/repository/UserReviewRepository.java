package com.urso.user.repository;

import com.urso.user.entity.User;
import com.urso.user.entity.UserReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReviewRepository extends JpaRepository<UserReview,Integer> {
}
