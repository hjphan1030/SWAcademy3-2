package com.cnu.sw2023.comment.domain;


import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.like.domain.CommentLike;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @Entity
public class Comment {

    @Id
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentLike> likes = new ArrayList<>();

    @NotNull
    private String content;
}
