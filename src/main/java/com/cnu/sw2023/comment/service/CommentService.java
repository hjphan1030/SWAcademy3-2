package com.cnu.sw2023.comment.service;

import com.cnu.sw2023.comment.Form.CommentForm;
import com.cnu.sw2023.comment.domain.Comment;
import com.cnu.sw2023.comment.repository.CommentRepository;
import com.cnu.sw2023.like.repository.CommentLikeRepository;
import com.cnu.sw2023.post.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service @AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentLikeRepository commentLikeRepository;

    public void postComment(CommentForm commentForm) {
        Long postId = commentForm.getPostId();
        String content = commentForm.getContent();

        Comment comment = Comment.builder()
                .post(postRepository.findById(postId).get())
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();
        commentRepository.save(comment);
    }
}
