package com.cnu.sw2023.post.form;

import com.cnu.sw2023.restaurant.dto.RestaurantDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostForm {

    private String title;
    private String content;
    private String restaurantName;
}
