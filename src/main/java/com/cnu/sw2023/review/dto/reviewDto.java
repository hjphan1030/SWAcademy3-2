package com.cnu.sw2023.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
public class reviewDto {
    private Long id;
    private String content;
    private int reviewLikeCount;
    private int rating;
    private LocalDateTime createdAt;
    private String restaurantName;

}
