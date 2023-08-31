package com.cnu.sw2023.review.repository;

import com.cnu.sw2023.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByOrderByReviewLikesDesc();

}
