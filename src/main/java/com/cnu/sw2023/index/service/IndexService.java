package com.cnu.sw2023.index.service;
import com.cnu.sw2023.index.dto.MainDTO;
import com.cnu.sw2023.index.dto.MainPostDto;
import com.cnu.sw2023.like.domain.PostLike;
import com.cnu.sw2023.like.repository.PostLikeRepository;
import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.post.repository.PostRepository;
import com.cnu.sw2023.restaurant.domain.Restaurant;
import com.cnu.sw2023.restaurant.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.post.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class IndexService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final RestaurantRepository restaurantRepository;

    public List<PostLike> findPostLikesSortedByLikesDescending() {
        List<PostLike> postLikes = postLikeRepository.findAllByOrderByPostLikeCountDesc();
        return postLikes;
    }

    public List<MainPostDto> getPopularPosts() {
        List<Post> popularPosts = postRepository.findTop5ByOrderByLikeCountDesc();

        return popularPosts.stream()
                .map(post -> new MainPostDto(post.getId(), post.getTitle(), post.getLikeCount(), post.getCreatedAt()))
                .collect(Collectors.toList());
    }

    // 자유게시판 최신순으로 정렬한 게시글 5개
    public List<MainDTO> findTop5ByOrderByCreatedAtDesc() {
        List<Post> top5TitleList = postRepository.findTop5ByOrderByCreatedAtDesc();
        return top5TitleList.stream()
                .map(post -> new MainDTO(post.getRestaurant().getRestaurantName(), post.getTitle(), post.getCreatedAt(), post.getLikeCount(), post.getComments().size()))
                .filter(mainDTO -> mainDTO.getRestaurantName().equals("자유게시판")) // 이 메소드를 사용해서 테스트를 하면 데이터가 1개만 추출됨.
                .sorted(Comparator.comparing(MainDTO::getCreatedAt).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }
}
