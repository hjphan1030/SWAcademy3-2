package com.cnu.sw2023.index.dto;

import com.cnu.sw2023.comment.Form.CommentProperty;
import com.cnu.sw2023.comment.domain.Comment;
import com.cnu.sw2023.restaurant.domain.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @AllArgsConstructor
public class MainDTO {
    private String restaurantName;
    private String title;
    private LocalDateTime createdAt;
    private int postLikeCount;
    private int commentCount;
}
