package com.cnu.sw2023.like.domain;

import com.cnu.sw2023.comment.domain.Comment;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity(name = "commentLikes")
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentLikesId")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "commentId")
    private Comment comment;

    public CommentLike(){
    }
    @Builder
    private CommentLike(Comment comment){
        this.comment = comment;
    }
}