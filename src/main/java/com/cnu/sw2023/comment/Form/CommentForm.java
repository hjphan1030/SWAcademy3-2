package com.cnu.sw2023.comment.Form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter @AllArgsConstructor
public class CommentForm {

    private Long postId;

    @NotNull
    private String content;
}
