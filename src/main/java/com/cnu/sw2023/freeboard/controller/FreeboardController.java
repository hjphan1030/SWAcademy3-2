package com.cnu.sw2023.freeboard.controller;

import com.cnu.sw2023.freeboard.service.FreeboardService;
import com.cnu.sw2023.post.domain.Post;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/main")
@AllArgsConstructor
public class FreeboardController {
    private final FreeboardService freeboardService;

//    @GetMapping("/freeboard")
//    public ResponseEntity<Map<String, Object>> getFreeBoardList() {
//        List<Post> freeboard = freeboardService.findPostSortedByDateDescending();
//        Map<String, String> freeboardTitle = new HashMap<>();
//
////        List<String> freeboardList =
//
//    }
}
