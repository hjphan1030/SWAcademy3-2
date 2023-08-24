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
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantService restaurantService;

    public List<Post> getPostByRestaurantName(String restaurantName) {
        return postRepository.findPostByRestaurant(restaurantRepository.findByRestaurantName(restaurantName));
    }
    public Long doPost(String restaurantId, PostForm postForm) {
        // restaurant_id로 음식점 정보 조회
        Restaurant restaurant = restaurantRepository.findByRestaurantName(restaurantId);
        if (restaurant == null) {
            throw new EntityNotFoundException("음식점을 찾을 수 없습니다.");
        }
        Post post = new Post();
        post.setTitle(postForm.getTitle());
        post.setContent(postForm.getContent());
        post.setRestaurant(restaurant);

        postRepository.save(post);
        return post.getPostId();
    }

    public Page<Post> getPostsByRestaurantName(String restaurantName, Pageable pageable) {
        Restaurant restaurant = restaurantRepository.findByRestaurantName(restaurantName);
        return postRepository.findByRestaurant(restaurant, pageable);
    }

    public void deletePost(Long postId) {
        postRepository.deleteByPostId(postId);
    }
}
