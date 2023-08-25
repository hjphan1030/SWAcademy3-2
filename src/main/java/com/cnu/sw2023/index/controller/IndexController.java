package com.cnu.sw2023.index.controller;

import com.cnu.sw2023.like.domain.PostLike;
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
    @GetMapping("/slide")
    public ResponseEntity<Map<String, Object>> getSlide(){
        Map<String, Object> response = new HashMap<>();
        Map<Long, String> res = new HashMap<>();
        Stream<String> titleStream = indexService
                    .findPostLikesSortedByLikesDescending().stream().map(postLike -> postLike.getPost().getTitle());
        Stream<Long> postIdStream = indexService
                .findPostLikesSortedByLikesDescending().stream().map(postLike -> postLike.getPost().getId());
        List<String> titleList = titleStream.collect(Collectors.toList());
        List<Long> postIdList = postIdStream.collect(Collectors.toList());
        for(int i = 0; i < titleList.size(); i++){
            res.put(postIdList.get(i), titleList.get(i));
        }
        response.put("slide", res);
        return ResponseEntity.ok().body(response);
    }

}
