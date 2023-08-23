package com.cnu.sw2023.post.service;

import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.post.dto.PostDTO;
import com.cnu.sw2023.post.repository.PostRepository;
import com.cnu.sw2023.restaurant.domain.Restaurant;
import com.cnu.sw2023.restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public PostService(PostRepository postRepository, RestaurantRepository restaurantRepository) {
        this.postRepository = postRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public List<Post> getPostByRestaurantId(String restaurant_id) {
        return postRepository.findPostByRestaurant(restaurantRepository.findByRestaurantId(restaurant_id));
    }

    public void doPost(String restaurant_id, PostDTO postDTO) {
        // restaurant_id로 음식점 정보 조회
        System.out.println(restaurant_id+"@");
        Restaurant restaurant = restaurantRepository.findByRestaurantId(restaurant_id);
        System.out.println(restaurant_id);
        if (restaurant == null) {
            throw new EntityNotFoundException("음식점을 찾을 수 없습니다.");
        }
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setRestaurant(restaurant);

        postRepository.save(post);
    }
}
