package com.cnu.sw2023.like.repository;


import com.cnu.sw2023.comment.domain.Comment;
import com.cnu.sw2023.like.domain.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByCommentIdAndEmail (Long commentId,String email);


}
