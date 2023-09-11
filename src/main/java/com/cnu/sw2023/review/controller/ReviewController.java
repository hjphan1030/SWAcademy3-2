package com.cnu.sw2023.review.controller;

import com.cnu.sw2023.post.dto.PostUpdateForm;
import com.cnu.sw2023.restaurant.service.RestaurantService;
import com.cnu.sw2023.review.domain.Review;
import com.cnu.sw2023.review.dto.reviewDto;
import com.cnu.sw2023.review.form.DetailReviewForm;
import com.cnu.sw2023.review.form.ReviewForm;
import com.cnu.sw2023.review.form.ReviewUpdateForm;
import com.cnu.sw2023.review.repository.ReviewRepository;
import com.cnu.sw2023.review.service.ReviewService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<Map<String, Object>> addReview(@RequestBody @Valid ReviewForm reviewForm
            , @PathVariable("restaurantName") String restaurantName
            , BindingResult bindingResult) {

        Map<String, Object> response = new HashMap<>();
        if (bindingResult.hasErrors()) {
            response.put("success", false);
            response.put("location", "");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        else if (!restaurantService.findRestaurantByRestaurantName(restaurantName)) {
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
    public ResponseEntity<List<reviewDto>> getReviewList(){
        List<Review> reviews = reviewService.findReviews();

        List<reviewDto> reviewList = reviews.stream()
                .map(review -> new reviewDto(review.getId(), review.getContent(), review.getLikeCount(),review.getRating(),review.getCreatedAt()))
                .sorted(Comparator.comparing(reviewDto::getCreatedAt).reversed())
                .collect(Collectors.toList());

        return ResponseEntity.ok(reviewList);
    }


    @ApiOperation("리뷰 상세 보기")
    @GetMapping("/DetailReview")
    public ResponseEntity<Map<String, Object>> showDetailReview(@RequestParam("reviewId") Long reviewId) {
        Map<String, Object> response = new HashMap<>();
        Optional<Review> targetReview = reviewService.getReviewByReviewId(reviewId);
        if (targetReview.isEmpty()) {
            response.put("success", false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Review review = targetReview.get();
        DetailReviewForm detailReviewForm = new DetailReviewForm(review);

        return ResponseEntity.status(HttpStatus.OK).body(detailReviewForm.toMap());

    }


    @ApiOperation("리뷰 삭제")
    @DeleteMapping("/")
    public ResponseEntity<Map<Object, Object>> deleteReview(@RequestParam("reviewId") Long reviewId) {
        reviewService.deleteReview(reviewId);
        Map<Object, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "삭제 완료");
        return ResponseEntity.ok().body(response);
    }


    @ApiOperation("리뷰 수정")
    @PostMapping("/{reviewId}/update")
    public ResponseEntity<Map<String,String>> updateReview(@PathVariable Long reviewId, @RequestBody ReviewUpdateForm reviewUpdateForm){
        String content = reviewUpdateForm.getContent();
        int rating = reviewUpdateForm.getRating();
        reviewService.updateReview(reviewId, rating, content);
        Map<String, String> res = new HashMap<>();
        res.put("message","수정 완료");
        return ResponseEntity.ok().body(res);
    }

}
