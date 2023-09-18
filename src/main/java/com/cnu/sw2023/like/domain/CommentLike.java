package com.cnu.sw2023.like.domain;

import com.cnu.sw2023.comment.domain.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity(name = "commentLikes")
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentLikesId")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "commentId")
    private Comment comment;

    private String email;

    public CommentLike(){
    }
}