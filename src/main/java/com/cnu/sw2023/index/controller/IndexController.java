package com.cnu.sw2023.index.controller;

import com.cnu.sw2023.index.dto.MainPostDto;
import com.cnu.sw2023.like.domain.PostLike;
import com.cnu.sw2023.post.service.PostService;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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

    @GetMapping("/popular")
    public ResponseEntity<List<MainPostDto>> getPopularPosts() {
        List<MainPostDto> popularPosts = indexService.getPopularPosts();
        return ResponseEntity.ok(popularPosts);
    }

    @GetMapping("/freeboard")
    public ResponseEntity<Map<String,Object>> getTop5TitlesByOrderDesc() {
        Map<String, Object> response = new HashMap<>();
        List<MainPostDto> top5Titles = indexService.findTop5ByOrderByCreatedAtDesc();
        response.put("freeboardList", top5Titles);
        return ResponseEntity.ok().body(response);
    }

//    // 자유게시판 최신글 5개
//    @GetMapping("/freeboard")
//    public ResponseEntity<Map<String, Object>> getFreeBoardTop5List() {
//
//        Map<String, Object> response = new HashMap<>();
//        Map<String, LocalDateTime> map = new HashMap<>();
//
//        // title, createdAt 전체 리스트 Map에 담기
//        List<String> titleList = indexService
//                .findTop5ByOrderByCreatedAtDesc()
//                .stream()
//                .map(post -> post.getTitle())
//                .collect(Collectors.toList());
//        List<LocalDateTime> createdAtList = indexService
//                .findTop5ByOrderByCreatedAtDesc()
//                .stream()
//                .map(post -> post.getCreatedAt())
//                .collect(Collectors.toList());
//        for (int i = 0; i < titleList.size(); i++) {
//            map.put(titleList.get(i), createdAtList.get(i));
//        }
//
//        // value값으로 전체 리스트 내림차순 정렬 후 5개만 담기
//        List<Map.Entry<String, LocalDateTime>> sortedEntries = map.entrySet()
//                .stream()
//                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
//                .limit(5)
//                .collect(Collectors.toList());
//
//        LinkedHashMap<String, LocalDateTime> res = new LinkedHashMap<>();
//        for (Map.Entry<String, LocalDateTime> entry : sortedEntries) {
//            res.put(entry.getKey(), entry.getValue());
//        }
//
//        response.put("freeboardList", res);
//        return ResponseEntity.ok().body(response);
//
//    }
}
