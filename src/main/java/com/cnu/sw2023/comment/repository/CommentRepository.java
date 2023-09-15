package com.cnu.sw2023.comment.repository;

import com.cnu.sw2023.comment.domain.Comment;
import com.cnu.sw2023.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    Page<Comment> findByEmail(String email, Pageable pageable);

}
