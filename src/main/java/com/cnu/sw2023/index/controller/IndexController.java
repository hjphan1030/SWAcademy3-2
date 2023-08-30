package com.cnu.sw2023.index.controller;

import com.cnu.sw2023.index.dto.MainPostDto;
import com.cnu.sw2023.like.domain.PostLike;
import com.cnu.sw2023.post.service.PostService;
import com.cnu.sw2023.restaurant.dto.RestaurantDTO;
import org.springframework.http.ResponseEntity;
import com.cnu.sw2023.index.service.IndexService;
import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.restaurant.domain.Restaurant;
import com.cnu.sw2023.restaurant.service.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;


@RestController
@AllArgsConstructor
@RequestMapping("/main")
public class IndexController {
    private final IndexService indexService;

    @GetMapping("/popular")
    public ResponseEntity<List<MainPostDto>> getPopularPosts() {
        List<MainPostDto> popularPosts = indexService.getPopularPosts();
        return ResponseEntity.ok(popularPosts);
    }
    @GetMapping("/slide/recent")
    public HashMap<String, String> getRecentRestaurant(){
        ArrayList<String> restaurantsNames = indexService.getRecentRestaurant();
        HashMap<String, String> map = new HashMap<>();
        for(String name : restaurantsNames){
            map.put(name, "boards/" + name +"/");
        }
        return map;
    }
}
