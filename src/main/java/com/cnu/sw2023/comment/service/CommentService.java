package com.cnu.sw2023.comment.service;

import com.cnu.sw2023.comment.Form.CommentForm;
import com.cnu.sw2023.comment.domain.Comment;
import com.cnu.sw2023.exception.CommentNotFoundException;
import com.cnu.sw2023.exception.UnauthorizedAccessException;
import com.cnu.sw2023.comment.repository.CommentRepository;
import com.cnu.sw2023.like.repository.CommentLikeRepository;
import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.post.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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

    public Comment postComment(CommentForm commentForm) {
        Long postId = commentForm.getPostId();
        String content = commentForm.getContent();

        Comment comment = Comment.builder()
                .post(postRepository.findById(postId).get())
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();
        commentRepository.save(comment);
        return comment;
    }

    public Comment showComment(Long commentId) {
        return commentRepository.findById(commentId).get();
    }

    public Long getPostId(Long commentId) {
        Comment comment = commentRepository.findById(commentId).get();
        return comment.getPost().getId();
    }

    public boolean checkAuth(Long id, String email) {
        Comment comment = commentRepository.findById(id).get();
        if (comment.getEmail().equals(email)) {
            return true;
        } else {
            return false;
        }
    }

    public Comment updateComment(Long commentId, String content) {
        Comment comment = commentRepository.findById(commentId).get();
        comment.setContent(content);
        return commentRepository.save(comment);
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

    public void deleteComment(Long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);

        if (optionalComment.isEmpty()) {
            throw new CommentNotFoundException("not found comment");
        }
        Comment comment = optionalComment.get();
        commentRepository.delete(comment);
    }

    // 댓글 목록 불러오기
    public List<Comment> getCommentList(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        List<Comment> commentList = post.get().getComments();
        if (commentList != null) {
            commentList.sort(Comparator.comparing(Comment::getCreatedAt).reversed());
            return commentList;
        } else {
            // commentList가 null인 경우 처리
            return Collections.emptyList(); // 빈 리스트를 반환하거나 다른 적절한 처리를 수행합니다.
        }
    }
}
