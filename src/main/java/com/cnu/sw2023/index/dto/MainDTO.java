package com.cnu.sw2023.index.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter @Setter @AllArgsConstructor
public class MainDTO {
    private Long postId;
    private String title;
    private LocalDateTime createdAt;
    private int postLikeCount;
    private int commentCount;
}
