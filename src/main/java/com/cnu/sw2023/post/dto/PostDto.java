package com.cnu.sw2023.post.dto;

import com.cnu.sw2023.comment.domain.Comment;
import com.cnu.sw2023.like.domain.PostLike;
import com.cnu.sw2023.restaurant.domain.Restaurant;
import com.sun.istack.NotNull;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class PostDto {

        private Long post_id;
        private String title;
        private String content;
        private Restaurant restaurant;
        private List<Comment> comments = new ArrayList<>();
        private List<PostLike> postLikes = new ArrayList<>();
        private LocalDateTime createdAt;
}
