package com.cnu.sw2023.post.dto;

import com.cnu.sw2023.post.domain.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class PostPageDto {
        private Long postId;
        private String title;
        private String content;
        private LocalDateTime createdAt;
        private int postLikeCount;
        private int commentCount;

        public PostPageDto(Long postId, String title, String content, LocalDateTime createdAt, int postLikeCount, int commentCount) {
                this.postId = postId;
                this.title = title;
                this.content = content;
                this.createdAt = createdAt;
                this.postLikeCount = postLikeCount;
                this.commentCount = commentCount;
        }

        public PostPageDto(Post post) {
                this.postId = post.getPostId();
                this.title = post.getTitle();
                this.content = post.getContent();
                this.createdAt = post.getCreatedAt();
                this.postLikeCount = post.getPostLikes().size();
                this.commentCount = post.getComments().size();
        }
}
