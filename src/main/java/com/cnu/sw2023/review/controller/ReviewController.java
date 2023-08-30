package com.cnu.sw2023.review.controller;

import com.cnu.sw2023.review.repository.ReviewRepository;
import com.cnu.sw2023.review.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    ReviewService reviewService;
    ReviewRepository reviewRepository;




}
