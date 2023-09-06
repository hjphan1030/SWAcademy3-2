package com.cnu.sw2023.index.controller;

import com.cnu.sw2023.index.dto.MainDTO;
import com.cnu.sw2023.index.dto.MainPostDto;
import com.cnu.sw2023.like.domain.PostLike;
import com.cnu.sw2023.post.service.PostService;
import lombok.extern.slf4j.Slf4j;

import com.cnu.sw2023.restaurant.dto.RestaurantDTO;
import com.cnu.sw2023.review.domain.Review;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@AllArgsConstructor
@RequestMapping("/main")
@Slf4j
public class IndexController {
    private final IndexService indexService;

    @GetMapping("/popular")
    public ResponseEntity<List<MainPostDto>> getPopularPosts() {
        List<MainPostDto> popularPosts = indexService.getPopularPosts();
        log.info("popular : {}", Arrays.toString(popularPosts.toArray()));
        return ResponseEntity.ok().body(popularPosts);
    }
    @GetMapping("/slide/recent")                    //최신순으로 등록된 식당 이름, 주소 3개 전달
    public HashMap<String, String> getRecentRestaurant() {
        ArrayList<String> restaurantsNames = indexService.getRecentRestaurant();
        HashMap<String, String> map = new HashMap<>();
        for (String name : restaurantsNames) {
            map.put(name, "boards/" + name + "/");
        }
        return map;
    }
    @GetMapping("/slide/bestReview")                    //좋아요 많은 순으로 리뷰 3개 전달
    public List<Review> getTop3BestRestaurant() {
        return indexService.getTop3BestReview();
    }

    @GetMapping("/freeboard")
    public ResponseEntity<List<MainDTO>> getTop5TitlesByOrderDesc() {
        Map<String, Object> response = new HashMap<>();
        List<MainDTO> top5Titles = indexService.getLatestPostsForRestaurant();
        log.info("top5 free: {}", Arrays.toString(top5Titles.toArray()));
        return ResponseEntity.ok().body(top5Titles);
    }
}
