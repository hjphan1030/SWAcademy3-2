package com.cnu.sw2023.post.controller;

import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.post.dto.PostPageDto;
import com.cnu.sw2023.post.service.PostSearchService;
import com.cnu.sw2023.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
public class PostSearchController {

    private final PostService postService;
    private final PostSearchService postSearchService;

    @GetMapping("/search/title")
    public ResponseEntity<Map<String ,Object>> searchPostsByTitle(@RequestParam String keyword) {
        List<Post> posts = postSearchService.searchPostByTitle(keyword);
        Stream<PostPageDto> dtoStream = posts.stream().map(PostPageDto::new);
        Map<String, Object> response = new HashMap<>();
        response.put("posts", dtoStream.collect(Collectors.toList()));
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/search/content")
    public ResponseEntity<Map<String ,Object>> searchPostsByContent(@RequestParam String keyword) {
        List<Post> posts = postSearchService.searchPostByContent(keyword);
        Stream<PostPageDto> dtoStream = posts.stream().map(PostPageDto::new);
        Map<String, Object> response = new HashMap<>();
        response.put("posts", dtoStream.collect(Collectors.toList()));
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/search")
    public ResponseEntity<Map<String ,Object>> searchPostsByTitleOrContent(@RequestParam String keyword) {
        List<Post> posts = postSearchService.searchPostByTitleOrContent(keyword);
        Stream<PostPageDto> dtoStream = posts.stream().map(PostPageDto::new);
        Map<String, Object> response = new HashMap<>();
        response.put("posts", dtoStream.collect(Collectors.toList()));
        return ResponseEntity.ok().body(response);
    }
}
