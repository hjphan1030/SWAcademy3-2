package com.cnu.sw2023.like.domain;

import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.review.domain.Review;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity(name = "reviewLikes")
public class ReviewLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewLikesId")
    private Long id;

    private String email;

    @ManyToOne
    @JoinColumn(name = "reviewId")
    private Review review;
}
