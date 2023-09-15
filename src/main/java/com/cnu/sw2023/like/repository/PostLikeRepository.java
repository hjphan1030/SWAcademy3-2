package com.cnu.sw2023.like.repository;

import com.cnu.sw2023.like.domain.PostLike;
import com.cnu.sw2023.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    @Override
    List<PostLike> findAll();

    List<PostLike> findAllByOrderByPostLikeCountDesc();

    Optional<PostLike> findByPostIdAndEmail(Long post_id, String email);

    Optional<PostLike> findById(Long postId);

}
