package com.cnu.sw2023.post.repository;

import com.cnu.sw2023.index.dto.MainDTO;
import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.restaurant.domain.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findPostByRestaurant (Restaurant restaurant);
    Page<Post> findByRestaurant(Restaurant restaurant, Pageable pageable);
    Optional<Post> findById (Long postId);
    void deleteById (Long postId);
    List<Post> findTop5ByOrderByLikeCountDesc();

    @Query("SELECT p.title, p.postLikes.size, p.comments.size FROM Post p WHERE p.restaurant.id = 1 ORDER BY p.createdAt DESC")
    List<Post> findTop5PostsByRestaurantIdOrderByCreatedAtDesc();
}
