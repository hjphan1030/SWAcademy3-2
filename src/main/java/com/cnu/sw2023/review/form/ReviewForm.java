package com.cnu.sw2023.review.form;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ReviewForm {

    private String content;
    private int rating;
}
