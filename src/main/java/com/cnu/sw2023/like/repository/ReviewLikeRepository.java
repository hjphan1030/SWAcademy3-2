package com.cnu.sw2023.like.repository;

import com.cnu.sw2023.like.domain.PostLike;
import com.cnu.sw2023.like.domain.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {

    Optional<ReviewLike> findByReviewIdAndEmail(Long reviewId, String email);
}

