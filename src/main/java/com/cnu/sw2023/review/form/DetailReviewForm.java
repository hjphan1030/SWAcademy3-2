package com.cnu.sw2023.review.form;

import com.cnu.sw2023.review.domain.Review;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class DetailReviewForm {
    private Long reviewId;
    private String content;
    private int reviewLikeCount;

    private int rating;
    private LocalDateTime createdAt;

    public Map<String, Object> toMap() {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("reviewId", this.reviewId);
        resultMap.put("content", this.content);
        resultMap.put("reviewLikeCount", this.reviewLikeCount);
        resultMap.put("rating", this.rating);
        resultMap.put("createdAt", this.createdAt);
        return resultMap;
    }

    @Builder
    public DetailReviewForm(Review review) {

        this.reviewId = review.getId();
        this.content = review.getContent();
        this.reviewLikeCount = review.getReviewLikeCount();
        this.createdAt = review.getCreatedAt();
        this.rating = review.getRating();
    }
}
