package com.cnu.sw2023.review.domain;

import com.cnu.sw2023.like.domain.PostLike;
import com.cnu.sw2023.like.domain.ReviewLike;
import com.cnu.sw2023.restaurant.domain.Restaurant;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewId")
    private Long id;

    @NotNull
    private String writer;

    @Column(name = "reviewContent")
    @NotNull @Size(min = 1,max = 2000)
    private String content;

    @NotNull
    private int rating;

//    @Column(name = "reviewLikes")
//    private int reviewLikeCount;

    @OneToMany(mappedBy = "review", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ReviewLike> reviewLikes = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurantName",referencedColumnName = "restaurantName")
    private Restaurant restaurant;

    public Review() {

    }
}
