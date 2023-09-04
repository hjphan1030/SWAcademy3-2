package com.cnu.sw2023.post.service;

import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.post.form.PostForm;
import com.cnu.sw2023.post.repository.PostRepository;
import com.cnu.sw2023.restaurant.domain.Restaurant;
import com.cnu.sw2023.restaurant.repository.RestaurantRepository;
import com.cnu.sw2023.restaurant.service.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantService restaurantService;

    public Optional<Post> getPostByPostId(Long postID) {
        return postRepository.findById(postID);
    }
    public Long addPost(String restaurantName, PostForm postForm, Authentication authentication) {
        // restaurant_id로 음식점 정보 조회
        Restaurant restaurant = restaurantRepository.findByRestaurantName(restaurantName);
        if (restaurant == null) {
            throw new EntityNotFoundException("음식점을 찾을 수 없습니다.");
        }
        Post post = Post.builder()
                .email(authentication.getName())
                .title(postForm.getTitle())
                .content(postForm.getContent())
                .restaurant(restaurant)
                .build();
        Post saved = postRepository.save(post);
        return saved.getId();
    }

    public Page<Post> getPostsByRestaurantName(String restaurantName, Pageable pageable) {
        Restaurant restaurant = restaurantRepository.findByRestaurantName(restaurantName);
        return postRepository.findByRestaurant(restaurant, pageable);
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    public boolean checkAuth(Long id, String email) {
        Optional<Post> post = postRepository.findById(id);
        if (post.get().getEmail().equals(email)) {
            return true;
        } else {
            return false;
        }
    }

    public void updatePost(Long postId, String content) {
        Post post = postRepository.findById(postId).get();
        post.setContent(content);
        postRepository.save(post);
    }
}
