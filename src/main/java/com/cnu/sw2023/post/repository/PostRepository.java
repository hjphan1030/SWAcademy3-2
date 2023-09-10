package com.cnu.sw2023.post.repository;

import com.cnu.sw2023.index.dto.MainDTO;
import com.cnu.sw2023.index.dto.MainPostDto;
import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.restaurant.domain.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findPostByRestaurant (Restaurant restaurant);
    Page<Post> findByRestaurant(Restaurant restaurant, Pageable pageable);
    Optional<Post> findById (Long postId);
    void deleteById (Long postId);
    List<Post> findTop5ByOrderByLikeCountDesc();
    List<Post> findAllByOrderByLikeCountDesc();
    @Query("SELECT p.title, p.postLikes.size, p.comments.size FROM Post p WHERE p.restaurant.id = 1 ORDER BY p.createdAt DESC")
    List<Post> findTop5PostsByRestaurantIdOrderByCreatedAtDesc();

    List<Post> findTop5ByOrderByCreatedAtDesc();

    Page<Post> findByTitleContainingAndRestaurantRestaurantName(String keyword, String restaurantName, Pageable pageable);
    Page<Post> findByContentContainingAndRestaurantRestaurantName(String keyword, String restaurantName, Pageable pageable);

    @Query("SELECT p FROM Post p " +
            "WHERE (p.content LIKE %:keyword% OR p.title LIKE %:keyword%) " +
            "AND p.restaurant.restaurantName = :restaurantName")
    Page<Post> findByContentContainingOrTitleContainingAndRestaurantRestaurantName(
            @Param("keyword") String keyword,
            @Param("restaurantName") String restaurantName,
            Pageable pageable);

    Page<Post> findPostsByEmail (String email,Pageable pageable);

    Page<Post> findByOrderByCreatedAtDesc(Pageable pageable);

    Page<Post> findByOrderByLikeCountDesc(Pageable pageable);  // 좋아요 내림차순으로 전부 가져오기

    @Query("SELECT e FROM Post e WHERE e.likeCount >= 10 ORDER BY e.likeCount DESC")  //좋아요가 10 이상인 것들만 내림차순으로 가져오기
    Page<Post> findWithLikeCountGreaterThanEqualOrderByLikeCountDesc(Pageable pageable);

}