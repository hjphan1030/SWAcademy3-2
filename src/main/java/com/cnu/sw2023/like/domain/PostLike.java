package com.cnu.sw2023.like.domain;

import com.cnu.sw2023.post.domain.Post;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity(name = "post_likes")
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_likes_id")
    private Long id;

    @Column(name = "post_like_count")
    private int postLikeCount; // 좋아요 개수를 나타내는 열

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
