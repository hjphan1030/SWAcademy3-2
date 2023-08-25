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

@Getter @Entity @Setter
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<CommentLike> commentLikes = new ArrayList<>();

    @NotNull
    private String content;

    private int likeCount;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public Comment(Post post, String content, LocalDateTime createdAt) {
        this.post = post;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Comment() {
    }
}
