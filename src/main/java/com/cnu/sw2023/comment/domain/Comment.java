package com.cnu.sw2023.comment.domain;


import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.like.domain.CommentLike;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter @Setter @Entity @Builder
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentId")
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentLike> likes = new ArrayList<>();

    @NotNull
    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public Comment(Long commentId, Post post, List<CommentLike> likes, String content, LocalDateTime createdAt) {
        this.commentId = commentId;
        this.post = post;
        this.likes = likes;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Comment() {

    }

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
