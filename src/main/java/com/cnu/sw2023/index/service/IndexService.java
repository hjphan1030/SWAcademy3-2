package com.cnu.sw2023.index.service;
import com.cnu.sw2023.index.dto.MainDTO;
import com.cnu.sw2023.index.dto.MainPostDto;
import com.cnu.sw2023.like.domain.PostLike;
import com.cnu.sw2023.like.repository.PostLikeRepository;
import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.post.repository.PostRepository;
import com.cnu.sw2023.restaurant.domain.Restaurant;
import com.cnu.sw2023.restaurant.dto.RestaurantDTO;
import com.cnu.sw2023.restaurant.repository.RestaurantRepository;
import com.cnu.sw2023.review.domain.Review;
import com.cnu.sw2023.review.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.post.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
@AllArgsConstructor
public class IndexService {
    private final RestaurantRepository restaurantRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final ReviewRepository reviewRepository;

    public List<PostLike> findPostLikesSortedByLikesDescending() {
        List<PostLike> postLikes = postLikeRepository.findAllByOrderByPostLikeCountDesc();
        return postLikes;
    }

    public List<MainPostDto> getPopularPosts() {
        List<Post> popularPosts = postRepository.findTop5ByOrderByLikeCountDesc();

        return popularPosts.stream()
                .map(post -> new MainPostDto(post.getId(), post.getTitle(), post.getLikeCount(), post.getCreatedAt()))
                .collect(Collectors.toList());
    }

    // 베스트 리뷰
    public List<Review> getTop3BestReview(){
        List<Review> reviewList = reviewRepository.findAllByOrderByReviewLikesDesc();
        return reviewList.stream().limit(3).collect(Collectors.toList());  // 앞에서 3개만 뽑아서 반환
    }
    // 최신등록순으로 상위 3개 식당이름 리스트 반환해주는 메소드
    public ArrayList<String> getRecentRestaurant() {
        List<Restaurant> restaurantsList = restaurantRepository.findTop3ByOrderByIdDesc();
        ArrayList<String> restaurantNames = new ArrayList<>();
        for (Restaurant r : restaurantsList) {
            restaurantNames.add(r.getRestaurantName());
        }
        return restaurantNames;
    }
    // 자유게시판 최신순으로 정렬한 게시글 5개
    @PersistenceContext
    private EntityManager entityManager;

    public List<MainDTO> getLatestPostsForRestaurant() {
        TypedQuery<Post> query = entityManager.createQuery(
                "SELECT p FROM Post p WHERE p.restaurant.restaurantId = :restaurantId ORDER BY p.createdAt DESC",
                Post.class
        );
        query.setParameter("restaurantId", "1");
        query.setMaxResults(5);

        return query.getResultList()
                .stream().map(m -> new MainDTO(m.getRestaurant().getRestaurantId(), m.getTitle(), m.getCreatedAt(), m.getLikeCount(), m.getComments().size()))
                .collect(Collectors.toList());

    }

}
