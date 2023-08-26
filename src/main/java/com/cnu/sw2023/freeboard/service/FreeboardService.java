package com.cnu.sw2023.freeboard.service;

import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.post.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FreeboardService {
    private final PostRepository postRepository;

    public List<Post> findTop4ByOrderByCreatedAtDesc() {
        List<Post> titleList = postRepository.findTop4ByOrderByCreatedAtDesc();
        return titleList;
    }

}

