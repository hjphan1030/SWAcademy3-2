package com.cnu.sw2023.hj.service;

import com.cnu.sw2023.exception.PostNotFoundException;
import com.cnu.sw2023.exception.UnauthorizedAccessException;
import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.post.form.PostForm;
import com.cnu.sw2023.post.repository.PostRepository;
import com.cnu.sw2023.restaurant.domain.Restaurant;
import com.cnu.sw2023.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostWriteService {
    private final PostRepository postRepository;
    private final RestaurantRepository restaurantRepository;

    public void write(String title, String content) {
//        postRepository.save(title, content);

    }

    public Long writePost(PostForm postForm, String restaurantName) {
        //restaurant_id로 음식점 정보 조회
        Restaurant restaurant = restaurantRepository.findByRestaurantName(restaurantName);
        if (restaurant == null) {
            throw new EntityNotFoundException("음식점을 찾을 수 없습니다.");
        }

        Post post = Post.builder().title(postForm.getTitle()).content(postForm.getContent()).restaurant(restaurant) .build();
        postRepository.save(post);
        return post.getId();
    }

    public void updatePost(Long postId, String title, String content, String restaurantName) {
        Restaurant restaurant = restaurantRepository.findByRestaurantName(restaurantName);
        Post post = postRepository.findById(postId).get();
        post.setTitle(title);
        post.setContent(content);
        post.setRestaurant(restaurant);
        postRepository.save(post);
    }

    public Post showPost(Long postId) {
        return postRepository.findById(postId).get();
    }

    public void deletePost(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            throw new PostNotFoundException("게시글을 찾지 못했습니다");
        }
        Post post = optionalPost.get();
        postRepository.deleteById(postId);
    }

}
