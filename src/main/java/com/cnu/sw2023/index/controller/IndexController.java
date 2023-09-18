package com.cnu.sw2023.index.controller;

import com.cnu.sw2023.index.dto.MainDTO;
import com.cnu.sw2023.index.dto.MainPostDto;
import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.post.dto.PostPageDto;
import com.cnu.sw2023.review.dto.reviewDto;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import com.cnu.sw2023.index.service.IndexService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.*;

@Controller
@AllArgsConstructor
@RequestMapping("/main")
@Slf4j
public class IndexController {
    private final IndexService indexService;
    @GetMapping("/slide/recent") @ResponseBody                 //최신순으로 등록된 식당 이름, 주소 3개 전달
    public ResponseEntity<List<Map>> getRecentRestaurant() {
        ArrayList<String> restaurantsNames = indexService.getRecentRestaurant();
        HashMap<String, String> map;
        List<Map> newRestaurantList = new ArrayList<>();
        int i = 0;
        for (String name : restaurantsNames) {
            i++;
            map = new HashMap<>();
            map.put("name", name);
            map.put("rank", Integer.toString(i));
            map.put("category", "new 음식점");
            newRestaurantList.add(map);
        }
        return ResponseEntity.ok().body(newRestaurantList);

    }
    @GetMapping("/slide/bestReview") @ResponseBody//좋아요 많은 순으로 리뷰 3개 전달
    public ResponseEntity<List<reviewDto>> getTop5BestReview() {
        List<reviewDto> reviewLists = indexService.getTop5BestReview();
        log.info("review : {}", Arrays.toString(reviewLists.toArray()));
        return ResponseEntity.ok().body(reviewLists);
    }
    @GetMapping("/free") @ResponseBody    // 자게 프리뷰
    public ResponseEntity<List<MainDTO>> getTop5TitlesByOrderDesc() {
        Map<String, Object> response = new HashMap<>();
        List<MainDTO> top5Titles = indexService.getLatestPostsForRestaurant();
        return ResponseEntity.ok().body(top5Titles);
    }
    @GetMapping("/popular") @ResponseBody  // 핫게 프리뷰
    public ResponseEntity<List<MainDTO>> getTop5PopularPosts() {
        List<MainDTO> popularPosts = indexService.getTop5PopularPosts();
        return ResponseEntity.ok().body(popularPosts);
    }
}
