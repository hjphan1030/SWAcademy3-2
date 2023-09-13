package com.cnu.sw2023.comment.service;

import com.cnu.sw2023.comment.Form.CommentForm;
import com.cnu.sw2023.comment.domain.Comment;
import com.cnu.sw2023.exception.CommentNotFoundException;
import com.cnu.sw2023.exception.UnauthorizedAccessException;
import com.cnu.sw2023.comment.repository.CommentRepository;
import com.cnu.sw2023.like.repository.CommentLikeRepository;
import com.cnu.sw2023.post.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service @AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentLikeRepository commentLikeRepository;

    public void postComment(CommentForm commentForm,String email) {
        Long postId = commentForm.getPostId();
        String content = commentForm.getContent();

        Comment comment = Comment.builder()
                .post(postRepository.findById(postId).get())
                .content(content)
                .createdAt(LocalDateTime.now())
                .email(email)
                .build();
        commentRepository.save(comment);
    }
//
//    public Comment postComment(CommentForm commentForm) {
//        Long postId = commentForm.getPostId();
//        String content = commentForm.getContent();
//
//        Comment comment = Comment.builder()
//                .post(postRepository.findById(postId).get())
//                .content(content)
//                .createdAt(LocalDateTime.now())
//                .build();
//        commentRepository.save(comment);
//        return comment;
//    }

    public boolean checkAuth(Long id, String email) {
        Comment comment = commentRepository.findById(id).get();
        if (comment.getEmail().equals(email)) {
            return true;
        } else {
            return false;
        }
    }

    public void updateComment(Long commentId, String content) {
        Comment comment = commentRepository.findById(commentId).get();
        comment.setContent(content);
        commentRepository.save(comment);
    }

    public void deleteComment(Long commentId, String email) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);

        if (optionalComment.isEmpty()) {
            throw new CommentNotFoundException("not found comment");
        }
        Comment comment = optionalComment.get();
        if (!comment.getEmail().equals(email)) {
            throw new UnauthorizedAccessException("댓글 작성자만 삭제할 수 있습니다");
        }
        commentRepository.delete(comment);
    }
}
