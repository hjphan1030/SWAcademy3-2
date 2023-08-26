package com.cnu.sw2023.freeboard.service;

import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.post.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FreeboardService {
    PostRepository postRepository;

    public List<Post> findPostSortedByDateDescending() {
        List<Post> freeboardList = postRepository.findPostsByTitleOrderByCreatedAtDesc();
        return freeboardList;
    }

}

