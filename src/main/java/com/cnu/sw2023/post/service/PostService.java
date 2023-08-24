package com.cnu.sw2023.post.service;

import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.post.dto.PostPageDto;
import com.cnu.sw2023.post.form.PostForm;
import com.cnu.sw2023.post.repository.PostRepository;
import com.cnu.sw2023.restaurant.domain.Restaurant;
import com.cnu.sw2023.restaurant.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final RestaurantRepository restaurantRepository;

    public List<Post> getPostByRestaurantName(String restaurant_name) {
        return postRepository.findPostByRestaurant(restaurantRepository.findByRestaurantName(restaurant_name));
    }
    public Long doPost(String restaurant_id, PostForm postForm) {
        // restaurant_id로 음식점 정보 조회
        Restaurant restaurant = restaurantRepository.findByRestaurantName(restaurant_id);
        if (restaurant == null) {
            throw new EntityNotFoundException("음식점을 찾을 수 없습니다.");
        }
        Post post = new Post();
        post.setTitle(postForm.getTitle());
        post.setContent(postForm.getContent());
        post.setRestaurant(restaurant);

        postRepository.save(post);
        return post.getId();
    }

    public Page<Post> getPostsByRestaurantName(String restaurantName, Pageable pageable) {
        return postRepository.findByRestaurantName(restaurantName, pageable);
    }
}
