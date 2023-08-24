package com.cnu.sw2023.restaurant.controller;


import com.cnu.sw2023.post.service.PostService;
import com.cnu.sw2023.restaurant.domain.Restaurant;
import com.cnu.sw2023.restaurant.service.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.HashMap;
import java.util.Map;


@RestController
@AllArgsConstructor
@RequestMapping("/main")
public class Controller {
    private final RestaurantService restaurantService;

    @GetMapping("/slide")
    public ResponseEntity<Map<String, Object>> getSlide(){
        Map<String, Object> response = new HashMap<>();
        List<String> restaurantList = restaurantService
                    .findRestaurantsSortedByLikesDescending()
                    .stream()
                    .map(Restaurant::getRestaurantName)
                    .collect(Collectors.toList());
        response.put("slide", restaurantList);  // restaurant name 리스트를 리턴
        return ResponseEntity.ok().body(response);
    }




}
