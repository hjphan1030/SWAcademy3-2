package com.cnu.sw2023.comment.Form;

import com.cnu.sw2023.comment.domain.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class CommentPageForm {
    String content;
    LocalDateTime createdAt;
    Long commentId;
    int likeCount;

    public CommentPageForm(Comment comment){
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.commentId = comment.getId();
        this.likeCount = comment.getLikeCount();
    }
}
