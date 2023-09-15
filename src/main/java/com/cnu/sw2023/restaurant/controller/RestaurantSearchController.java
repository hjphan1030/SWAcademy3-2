package com.cnu.sw2023.restaurant.controller;

import com.cnu.sw2023.member.domain.College;
import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.post.dto.PostPageDto;
import com.cnu.sw2023.restaurant.domain.Restaurant;
import com.cnu.sw2023.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RestaurantSearchController {
    private final RestaurantService restaurantService;
    @GetMapping("/restaurantSearch/title")
    public List<String> searchRestaurantByTitle(@RequestParam(name = "keyword") String keyword) {
        return restaurantService.getRestaurantNamesByTitle(keyword);
    }

    @GetMapping("/restaurantSearch")
    public List<String> findRestaurantsByCategoryAndRegion(
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "region", required = false) String region) {
        List<Restaurant> result = restaurantService.findRestaurantsByCategoryAndRegion(category, region);
        return result.stream().map(Restaurant::getRestaurantName).collect(Collectors.toList());
    }

    @GetMapping("/restaurantConditionalSearch")
    public List<String> findRestaurantsByCondition(
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "region", required = false) String region,
            @RequestParam(name = "college", required = false) String collegeStr) {
        if (collegeStr==null) {
            List<Restaurant> result = restaurantService.findRestaurantsByCategoryAndRegion(category, region);
            return result.stream().map(Restaurant::getRestaurantName).collect(Collectors.toList());
        } else  {
            List<String> result = restaurantService.findRestaurantsByCategoryAndRegion(category, region)
                    .stream().map(Restaurant::getRestaurantName).collect(Collectors.toList());
            College college = College.fromString(collegeStr);
            return restaurantService.findRestaurantsByCollegeWithMostPosts(college,result);
        }
    }
}
