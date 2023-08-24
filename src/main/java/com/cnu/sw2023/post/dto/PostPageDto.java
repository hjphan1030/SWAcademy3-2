package com.cnu.sw2023.post.dto;

import com.cnu.sw2023.post.domain.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class PostPageDto {
        private Long post_id;
        private String title;
        private String content;
        private LocalDateTime createdAt;
        private int postLikeCount;
        private int commentCount;

        public PostPageDto(Long post_id, String title, String content, LocalDateTime createdAt, int postLikeCount, int commentCount) {
                this.post_id = post_id;
                this.title = title;
                this.content = content;
                this.createdAt = createdAt;
                this.postLikeCount = postLikeCount;
                this.commentCount = commentCount;
        }

        public PostPageDto(Post post) {
                this.post_id = post.getPostId();
                this.title = post.getTitle();
                this.content = post.getContent();
                this.createdAt = post.getCreatedAt();
                this.postLikeCount = post.getPostLikes().size();
                this.commentCount = post.getComments().size();
        }
}
