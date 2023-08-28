package com.cnu.sw2023.like.domain;

import com.cnu.sw2023.post.domain.Post;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity(name = "postLikes")
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postLikesId")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;
}
