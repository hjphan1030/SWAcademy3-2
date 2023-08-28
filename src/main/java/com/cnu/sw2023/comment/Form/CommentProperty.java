package com.cnu.sw2023.comment.Form;

import com.cnu.sw2023.comment.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor
public class CommentProperty {
    private Long commentId;
    private String content;
    private LocalDateTime createdAt;
    private int likeCount;


    public CommentProperty(Comment comment){
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.likeCount = comment.getLikeCount();
    }
}
