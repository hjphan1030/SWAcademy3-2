package com.cnu.sw2023.post.repository;

import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.restaurant.domain.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findPostByRestaurant (Restaurant restaurant);
    Page<Post> findByRestaurant(Restaurant restaurant, Pageable pageable);
    Optional<Post> findByPostId (Long postId);
    void deleteById (Long postId);
    List<Post> findTop5ByOrderByLikeCountDesc();

    List<Post> findTop5ByOrderByCreatedAtDesc();
}
