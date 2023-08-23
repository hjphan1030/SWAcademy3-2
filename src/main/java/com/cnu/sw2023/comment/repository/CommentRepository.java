package com.cnu.sw2023.comment.repository;

import com.cnu.sw2023.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
