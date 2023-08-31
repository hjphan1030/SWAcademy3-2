package com.cnu.sw2023.post.controller;

import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.post.dto.PostPageDto;
import com.cnu.sw2023.post.service.PostSearchService;
import com.cnu.sw2023.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{restaurantName}/search/title")
    public List<PostPageDto> searchRestaurantPostsByTitle(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @PathVariable String restaurantName) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Post> pages = postSearchService.searchRestaurantPostByTitle(keyword, restaurantName, pageable);
        Stream<PostPageDto> dtoStream = pages.stream().map(PostPageDto::new);
        return dtoStream.collect(Collectors.toList());
    }

    @GetMapping("/{restaurantName}/search/content")
    public List<PostPageDto> searchRestaurantPostsByContent(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @PathVariable String restaurantName) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Post> pages = postSearchService.searchRestaurantPostByContent(keyword, restaurantName, pageable);
        Stream<PostPageDto> dtoStream = pages.stream().map(PostPageDto::new);
        return dtoStream.collect(Collectors.toList());
    }

    @GetMapping("/{restaurantName}/search")
    public List<PostPageDto> searchRestaurantPosts(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @PathVariable String restaurantName) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Post> pages = postSearchService.searchRestaurantPost(keyword, restaurantName, pageable);
        Stream<PostPageDto> dtoStream = pages.stream().map(PostPageDto::new);
        return dtoStream.collect(Collectors.toList());
    }
}