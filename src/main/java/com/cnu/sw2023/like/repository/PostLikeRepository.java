package com.cnu.sw2023.like.repository;


import com.cnu.sw2023.like.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    @Override
    List<PostLike> findAll();
    List<PostLike> findAllByOrderByPostLikeCountDesc();
}