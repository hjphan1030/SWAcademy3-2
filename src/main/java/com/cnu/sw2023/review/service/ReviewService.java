package com.cnu.sw2023.review.service;

import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.post.form.PostForm;
import com.cnu.sw2023.post.repository.PostRepository;
import com.cnu.sw2023.restaurant.domain.Restaurant;
import com.cnu.sw2023.restaurant.repository.RestaurantRepository;
import com.cnu.sw2023.review.domain.Review;
import com.cnu.sw2023.review.form.ReviewForm;
import com.cnu.sw2023.review.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class ReviewService {

    private final PostRepository postRepository;
    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;

    public Long addReview(String restaurantName, ReviewForm reviewForm) {
        // restaurant_id로 음식점 정보 조회
        Restaurant restaurant = restaurantRepository.findByRestaurantName(restaurantName);
        if (restaurant == null) {
            throw new EntityNotFoundException("음식점을 찾을 수 없습니다.");
        }
        Review review = Review.builder().content(reviewForm.getContent()).rating(reviewForm.getRating()).restaurant(restaurant).build();
        Review saved = reviewRepository.save(review);
        return saved.getId();
    }
}
