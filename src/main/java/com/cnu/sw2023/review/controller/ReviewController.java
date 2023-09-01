package com.cnu.sw2023.review.controller;

import com.cnu.sw2023.restaurant.service.RestaurantService;
import com.cnu.sw2023.review.domain.Review;
import com.cnu.sw2023.review.dto.ReviewDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cnu.sw2023.review.form.ReviewForm;
import com.cnu.sw2023.review.repository.ReviewRepository;
import com.cnu.sw2023.review.service.ReviewService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    ReviewService reviewService;
    ReviewRepository reviewRepository;
    RestaurantService restaurantService;

    @ApiOperation("특정 음식점에 리뷰 작성")
    @PostMapping("/{restaurantName}/post")
    public ResponseEntity<Map<String, Object>> addReview(@RequestBody ReviewForm reviewForm, @PathVariable("restaurantName") String restaurantName) {

        Map<String, Object> response = new HashMap<>();

        if (!restaurantService.findRestaurantByRestaurantName(restaurantName)) {
            response.put("success", false);
            response.put("location", "");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Long reviewId = reviewService.addReview(restaurantName, reviewForm);
        URI locationUri = URI.create("/review/posts/" + reviewId);
        response.put("success", true);
        response.put("location", locationUri);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @ApiOperation("리뷰 최신순 정렬 리스트")
    @GetMapping("/reviewList")
    public ResponseEntity<List<ReviewDTO>> getReviewList(){
        List<Review> reviews = reviewService.findReviews();

        List<ReviewDTO> reviewList = reviews.stream()
                .map(review -> new ReviewDTO(review.getId(), review.getContent(), review.getReviewLikeCount(),review.getRating(),review.getCreatedAt()))
                .sorted(Comparator.comparing(ReviewDTO::getCreatedAt).reversed())
                .collect(Collectors.toList());

        return ResponseEntity.ok(reviewList);
    }


//    @ApiOperation("리뷰 상세 보기")
//    @GetMapping("/")


//    @ApiOperation("리뷰 삭제")
//    @DeleteMapping("/")


//    @ApiOperation("리뷰 수정")
//    @PatchMapping("")


}
