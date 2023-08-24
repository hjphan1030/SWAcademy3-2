package com.cnu.sw2023.post.form;

import com.cnu.sw2023.comment.domain.Comment;
import com.cnu.sw2023.like.domain.PostLike;
import com.cnu.sw2023.restaurant.domain.Restaurant;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter @Setter
@Builder
public class DetailPostForm {

    private Long postId;
    private String title;
    private String content;
    private int postLikeCount;
    private LocalDateTime createdAt;
    private List<Comment.CommentProperty> comment;

    public Map<String, Object> toMap() {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("postId", this.postId);
        resultMap.put("title", this.title);
        resultMap.put("content", this.content);
        resultMap.put("postLikeCount", this.postLikeCount);
        resultMap.put("createdAt", this.createdAt);
        resultMap.put("comment", this.comment);
        return resultMap;

    }
}