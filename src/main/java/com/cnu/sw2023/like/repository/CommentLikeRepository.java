package com.cnu.sw2023.like.repository;


import com.cnu.sw2023.like.domain.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
}
