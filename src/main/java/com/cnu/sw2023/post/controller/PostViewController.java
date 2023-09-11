package com.cnu.sw2023.post.controller;

import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.post.dto.PostPageDto;
import com.cnu.sw2023.post.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@AllArgsConstructor
@Controller
public class PostViewController {
    private final PostService postService;
    @GetMapping("/freeBoard")   // 자게 더보기
    public String getFreeBoard(Model model, @RequestParam(defaultValue = "0") int page){
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> pages = postService.getFreePost(pageable); //(restaurantName, pageable);
        boolean lastPage = pages.isLast();
        Stream<PostPageDto> dtoStream = pages
                .stream()
                .map(PostPageDto::new);
        List<PostPageDto> allPosts = dtoStream.collect(Collectors.toList());
        model.addAttribute("paging", pages);
        model.addAttribute("posts", allPosts);
        model.addAttribute("page", page);
        System.out.println("hasPrevious"+pages.hasPrevious());
        System.out.println(allPosts.toArray().toString());
        return "freeBoard";
    }
    @GetMapping("/popularBoard")   // 핫게 더보기
    public String getPopularBoard(Model model, @RequestParam(defaultValue = "0") int page){
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> pages = postService.getAllPopularPosts(pageable); //(restaurantName, pageable);
        Stream<PostPageDto> dtoStream = pages
                .stream()
                .map(PostPageDto::new);
        List<PostPageDto> allPosts = dtoStream.collect(Collectors.toList());
        List<String> CreatedAts = allPosts
                .stream()
                .map(post -> post.getCreatedAt().toString().substring(5,7)+"/"+post.getCreatedAt().toString().substring(8,10)+" "+post.getCreatedAt().toString().substring(11,16))
                .collect(Collectors.toList());
        model.addAttribute("posts", allPosts);
        model.addAttribute("page", page);
        model.addAttribute("createdAts", CreatedAts);
        return "popularBoard";
    }
}


