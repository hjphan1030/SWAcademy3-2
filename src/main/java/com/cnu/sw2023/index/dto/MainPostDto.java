package com.cnu.sw2023.index.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor
public class MainPostDto {
    private Long id;
    private String title;
    private int postLikeCount;
    private LocalDateTime createdAt;
}
