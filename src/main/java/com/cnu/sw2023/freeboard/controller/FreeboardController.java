package com.cnu.sw2023.freeboard.controller;

import com.cnu.sw2023.freeboard.service.FreeboardService;
import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.post.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/main")
@AllArgsConstructor
public class FreeboardController {
    private final FreeboardService freeboardService;
//    private final PostService postService;

    // 자유게시판 최신글 5개
    @GetMapping("/freeboard")
    public ResponseEntity<Map<String, Object>> getFreeBoardTop4List() {

        Map<String, Object> response = new HashMap<>();
        Map<String, LocalDateTime> map = new HashMap<>();

        // title, createdAt 전체 리스트 Map에 담기
        List<String> titleList = freeboardService
                .findTop5ByOrderByCreatedAtDesc()
                .stream()
                .map(post -> post.getTitle())
                .collect(Collectors.toList());
        List<LocalDateTime> createdAtList = freeboardService
                .findTop5ByOrderByCreatedAtDesc()
                .stream()
                .map(post -> post.getCreatedAt())
                .collect(Collectors.toList());
        for (int i = 0; i < titleList.size(); i++) {
            map.put(titleList.get(i), createdAtList.get(i));
        }

        // value값으로 전체 리스트 내림차순 정렬 후 5개만 담기
        List<Map.Entry<String, LocalDateTime>> sortedEntries = map.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(5)
                .collect(Collectors.toList());

        LinkedHashMap<String, LocalDateTime> res = new LinkedHashMap<>();
        for (Map.Entry<String, LocalDateTime> entry : sortedEntries) {
            res.put(entry.getKey(), entry.getValue());
        }

        response.put("freeboardList", res);
        return ResponseEntity.ok().body(response);

    }
}
