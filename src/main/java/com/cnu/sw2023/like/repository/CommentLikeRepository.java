package com.cnu.sw2023.like.repository;


import com.cnu.sw2023.comment.domain.Comment;
import com.cnu.sw2023.like.domain.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    List<CommentLike> findAllByComment(Comment comment);
}
