package com.cnu.sw2023.post.controller;

import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.post.dto.PostDTO;
import com.cnu.sw2023.post.service.PostService;
import com.cnu.sw2023.restaurant.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final RestaurantRepository restaurantRepository;

    @GetMapping("/{restaurant_id}")
    public ResponseEntity<List<Post>> getPost(@PathVariable String restaurant_id){
        List<Post> posts = postService.getPostByRestaurantId(restaurant_id);
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/{restaurant_id}")
    public ResponseEntity<Map<String,String>> doPost(@PathVariable String restaurant_id, @RequestBody PostDTO postDTO) {
        if ( ! restaurantRepository.existsRestaurantByRestaurantId(restaurant_id)) {
            Map<String , String> response = new HashMap<>();
            response.put("message","실패");
            return ResponseEntity.badRequest().body(response);
        }
        postService.doPost(restaurant_id, postDTO);
        URI locationUri = URI.create("/posts/" + restaurant_id);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", locationUri.toString());
        return ResponseEntity.created(locationUri).headers(headers).build();
    }
}