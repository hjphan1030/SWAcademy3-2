package com.cnu.sw2023.post.form;

import com.cnu.sw2023.comment.Form.CommentProperty;
import com.cnu.sw2023.comment.domain.Comment;
import com.cnu.sw2023.post.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter @Setter
public class DetailPostForm {

    private Long postId;
    private String title;
    private String content;
    private int postLikeCount;
    private LocalDateTime createdAt;
    private List<CommentProperty> comment;

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

    @Builder
    public DetailPostForm(Post post) {
        List<Comment> comments = post.getComments();
        List<CommentProperty> commentProperties = new ArrayList<>();

        for (Comment comment : comments) {
            CommentProperty commentProperty = new CommentProperty(comment);
            commentProperty.setCommentId(comment.getId());
            commentProperty.setContent(comment.getContent());
            commentProperty.setCreatedAt(comment.getCreatedAt());
            commentProperty.setLikeCount(comment.getLikeCount());

            commentProperties.add(commentProperty);
        }

        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.postLikeCount = post.getLikeCount();
        this.createdAt = post.getCreatedAt();
        this.comment = commentProperties;
    }
}