package com.cnu.sw2023.comment.domain;


import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.like.domain.CommentLike;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter @Setter @Entity
public class Comment {

    @Id
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentLike> likes = new ArrayList<>();

    @NotNull
    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

    @Getter
    public static class CommentProperty {
        private String content;
        private int commentLikeCount;
        private Timestamp createdAt;

        public CommentProperty(Comment comment) {
            content = comment.getContent();
            commentLikeCount = comment.getLikes().size();
            createdAt = Timestamp.valueOf(comment.getCreatedAt());
        }
    }
}
