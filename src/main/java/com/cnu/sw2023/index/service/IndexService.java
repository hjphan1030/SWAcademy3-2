package com.cnu.sw2023.index.service;
import com.cnu.sw2023.index.dto.MainPostDto;
import com.cnu.sw2023.like.domain.PostLike;
import com.cnu.sw2023.like.repository.PostLikeRepository;
import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.post.repository.PostRepository;
import com.cnu.sw2023.restaurant.domain.Restaurant;
import com.cnu.sw2023.restaurant.dto.RestaurantDTO;
import com.cnu.sw2023.restaurant.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class IndexService {
    private final RestaurantRepository restaurantRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
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

    public ArrayList<String> getRecentRestaurant() {
        List<Restaurant> restaurantsList = restaurantRepository.findTop3ByOrderByIdDesc();
        ArrayList<String> restaurantNames = new ArrayList<>();
        for(Restaurant r : restaurantsList){
            restaurantNames.add(r.getRestaurantName());
        }
        return restaurantNames;
    }
}
