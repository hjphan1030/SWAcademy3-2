package com.cnu.sw2023.like.service;

import com.cnu.sw2023.comment.domain.Comment;
import com.cnu.sw2023.comment.repository.CommentRepository;
import com.cnu.sw2023.like.domain.CommentLike;
import com.cnu.sw2023.like.domain.PostLike;
import com.cnu.sw2023.like.repository.CommentLikeRepository;
import com.cnu.sw2023.like.repository.PostLikeRepository;
import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.post.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LikeService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final PostLikeRepository postLikeRepository;
    public void postLike(Long postId,String email) throws EntityNotFoundException {
        Optional<PostLike> existingLike = postLikeRepository.findByPostIdAndEmail(postId, email);
        Optional<Post> optionalPost = postRepository.findById(postId);
        Post post = optionalPost.orElseThrow(EntityNotFoundException::new);
        if (existingLike.isEmpty()) {
            post.setLikeCount(post.getLikeCount() + 1);
            postRepository.save(post);
            PostLike newLike = new PostLike();
            newLike.setPost(post);
            newLike.setEmail(email);
            postLikeRepository.save(newLike);
        } else {
            post.setLikeCount(post.getLikeCount() - 1);
            postRepository.save(post);
            PostLike postLike = existingLike.get();
            postLikeRepository.delete(postLike);
        }
    }

    public void commentLike(Long commentId,String email) {
        Optional<CommentLike> existingCommentLike = commentLikeRepository.findByCommentIdAndEmail(commentId, email);
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        Comment comment = optionalComment.orElseThrow(EntityNotFoundException::new);
        if (existingCommentLike.isEmpty()) {
            comment.setLikeCount(comment.getLikeCount() + 1);
            commentRepository.save(comment);
            CommentLike commentLike = new CommentLike();
            commentLike.setComment(comment);
            commentLike.setEmail(email);
            commentLikeRepository.save(commentLike);
        } else {
            comment.setLikeCount(comment.getLikeCount() - 1);
            commentRepository.save(comment);
            CommentLike commentLike = existingCommentLike.get();
            commentLikeRepository.delete(commentLike);
        }
    }

    public void postLike(Long postId) throws EntityNotFoundException {
        Optional<PostLike> existingLike = postLikeRepository.findById(postId);
        Optional<Post> optionalPost = postRepository.findById(postId);
        Post post = optionalPost.orElseThrow(EntityNotFoundException::new);
        if (existingLike.isEmpty()) {
            post.setLikeCount(post.getLikeCount() + 1);
            postRepository.save(post);
            PostLike newLike = new PostLike();
            newLike.setPost(post);
            postLikeRepository.save(newLike);
        } else {
            post.setLikeCount(post.getLikeCount() - 1);
            postRepository.save(post);
            PostLike postLike = existingLike.get();
            postLikeRepository.delete(postLike);
        }
    }
}
