package com.cnu.sw2023.like.service;

import com.cnu.sw2023.comment.domain.Comment;
import com.cnu.sw2023.comment.repository.CommentRepository;
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
    private final CommentLikeRepository commentLikeRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;
    public void postLike(Long postId) throws EntityNotFoundException {
            Optional<Post> postOptional = postRepository.findById(postId);
            if (postOptional.isPresent()) {
                Post post = postOptional.get();
                int currentLikeCount = post.getLikeCount();
                post.setLikeCount(currentLikeCount + 1);
                postRepository.save(post);
            } else {
                throw new EntityNotFoundException("게시글이 존재 하지 않습니다");
            }
    }


    public void commentLike(Long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isPresent()) {
            Comment comment =  optionalComment.get();
            int currentLikeCount = comment.getLikeCount();
            comment.setLikeCount(currentLikeCount + 1);
            commentRepository.save(comment);
        } else {
            throw new EntityNotFoundException("게시글이 존재 하지 않습니다");
        }

    }
}
