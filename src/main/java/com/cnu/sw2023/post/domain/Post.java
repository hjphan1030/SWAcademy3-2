package com.cnu.sw2023.post.domain;


import com.cnu.sw2023.comment.domain.Comment;
import com.cnu.sw2023.like.domain.PostLike;
import com.cnu.sw2023.restaurant.domain.Restaurant;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity @AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postId")
    private Long id;

    @NotNull @Size(min = 2,max = 30)
    private String title;

    @NotNull @Size(min = 1,max = 2000)
    private String content;

    private String email;

    private int likeCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurantName",referencedColumnName = "restaurantName")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<PostLike> postLikes = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;
    @Builder
    public Post(String title,String email ,String content, Restaurant restaurant, List<Comment> comments, List<PostLike> postLikes) {
        this.title = title;
        this.content = content;
        this.restaurant = restaurant;
        this.comments = comments;
        this.postLikes = postLikes;
        this.email = email;
    }

    public Post() {
    }
}

