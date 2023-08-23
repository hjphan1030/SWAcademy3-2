package com.cnu.sw2023.like.domain;

import com.cnu.sw2023.comment.domain.Comment;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity(name = "comment_likes")
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_likes_id")
    private Long id;

    @Column(name = "comment_like_count")
    private int commentLikeCount; // 좋아요 개수를 나타내는 열

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;
}