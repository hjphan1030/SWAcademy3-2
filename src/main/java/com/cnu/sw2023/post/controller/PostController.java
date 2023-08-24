package com.cnu.sw2023.post.controller;

import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.post.form.PostForm;
import com.cnu.sw2023.post.dto.PostPageDto;
import com.cnu.sw2023.post.service.PostService;
import com.cnu.sw2023.restaurant.service.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@AllArgsConstructor
@RequestMapping("/boards")
public class PostController {
    private final PostService postService;
    private final RestaurantService restaurantService;


//    @GetMapping("/{restaurant_id}")
//    public ResponseEntity<List<Post>> getPost(@PathVariable String restaurant_id){
//        List<Post> posts = postService.getPostByRestaurantId(restaurant_id);
//        return ResponseEntity.ok(posts);
//    }

    @PostMapping("/{restaurant_id}")
    public ResponseEntity<Map<String, Object>> doPost(@RequestBody PostForm postForm, @PathVariable("restaurant_id") String restaurantName) {

        Map<String , Object> response = new HashMap<>();
        if ( ! restaurantService.findRestaurantByRestaurantName(restaurantName)) {
                response.put("success",false);
                response.put("location","");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        Long post_id = postService.doPost(restaurantName, postForm);
        URI locationUri = URI.create("/boards/posts/" +String.valueOf(post_id));

        response.put("success",true);
        response.put("location",locationUri);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{restaurantName}")
    public ResponseEntity<Map<String ,Object>> getPostsByRestaurantName(@PathVariable String restaurantName, @RequestParam(defaultValue = "0") int page_num){
        final int size = 10;

        Pageable pageable = PageRequest.of(page_num, size);
        Page<Post> page = postService.getPostsByRestaurantName(restaurantName, pageable);
        boolean lastPage = page.isLast();
        Stream<PostPageDto> dtoStream = page.stream().map(post -> new PostPageDto(post));

        Map<String, Object> response = new HashMap<>();
        response.put("posts", dtoStream.collect(Collectors.toList())); // 스트림을 리스트로 변환하여 맵에 넣음
        response.put("lastPage", lastPage);
        return ResponseEntity.ok().body(response);

    }
}