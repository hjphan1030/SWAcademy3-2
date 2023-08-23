package com.cnu.sw2023.like.repository;

import com.cnu.sw2023.like.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
}
