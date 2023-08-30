package com.cnu.sw2023.review.service;

import com.cnu.sw2023.review.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReviewService {

    ReviewRepository reviewRepository;


}
