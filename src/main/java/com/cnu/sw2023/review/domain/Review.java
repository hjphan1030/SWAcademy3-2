package com.cnu.sw2023.review.domain;

import com.cnu.sw2023.like.domain.PostLike;
import com.cnu.sw2023.like.domain.ReviewLike;
import com.cnu.sw2023.restaurant.domain.Restaurant;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewId")
    private Long id;

    @NotNull
    private String userEmail;

    @Column(name = "content")
    @NotNull @Size(min = 1,max = 2000)
    private String content;

    @NotNull
    @Positive
    @Max(value = 5)
    private int rating;

    @Column
    private int likeCount;

    @OneToMany(mappedBy = "review", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ReviewLike> reviewLikes = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurantName",referencedColumnName = "restaurantName")
    private Restaurant restaurant;

    @Builder
    public Review(String content, int rating, Restaurant restaurant, List<ReviewLike> reviewLikes) {
        this.content = content;
        this.rating = rating;
        this.restaurant = restaurant;
        this.reviewLikes = reviewLikes;
    }
    public Review() {

    }
}
