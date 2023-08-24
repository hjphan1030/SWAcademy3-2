package com.cnu.sw2023.post.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class PostResponse {
    private List<PostPageDto> posts;
    private boolean lastPage;

    public PostResponse(List<PostPageDto> posts, boolean lastPage) {
        this.posts = posts;
        this.lastPage = lastPage;
    }
}
