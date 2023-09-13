package com.cnu.sw2023.review.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter @Setter
public class ReviewForm {

    @NotNull
    private String content;

    @Min(value = 1,message = "레이팅은 1 이상 5이하로 해야합니다")
    @Max(value = 5, message = "레이팅은 1 이상 5이하로 해야합니다")
    private int rating;
}
