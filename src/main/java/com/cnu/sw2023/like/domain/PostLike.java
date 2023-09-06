package com.cnu.sw2023.like.domain;

import com.cnu.sw2023.post.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity(name = "postLikes")
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postLikesId")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;

    private String email;

    public PostLike() {
    }
}
