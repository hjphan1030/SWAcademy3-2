package com.cnu.sw2023.like.controller;

import com.cnu.sw2023.like.service.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PutMapping("/post/{postId}/like")
    public ResponseEntity<Map<String,Object>> postLike(@PathVariable Long postId, Authentication authentication){
        String email = authentication.getName();
        likeService.postLike(postId,email);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/comment/{commentId}/like")
    public ResponseEntity<Map<String,Object>> commentLike(@PathVariable Long commentId,Authentication authentication){
        String email = authentication.getName();
        likeService.commentLike(commentId,email);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/review/{reviewId}/like")
    public ResponseEntity<Map<String,Object>> reviewLike(@PathVariable Long reviewId,Authentication authentication) {
        String email = authentication.getName();
        likeService.reviewLike(reviewId, email);
        return ResponseEntity.ok().build();
    }
}
