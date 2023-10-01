package com.cnu.sw2023.restaurant.controller;

import com.cnu.sw2023.restaurant.domain.Restaurant;
import com.cnu.sw2023.restaurant.dto.RestaurantInfo;
import com.cnu.sw2023.restaurant.service.RestaurantService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RestaurantRestController {
    private final RestaurantService restaurantService;

    @GetMapping("/test/")
    @ResponseBody
    public List<Restaurant> searchRestaurantByCategoryAndCollege(@RequestParam String category, @RequestParam(required = false) String college){
        return restaurantService.getRankingByCategoryAndCollege(category,college);
    }

    @GetMapping("/restaurantInfo/{restaurantName}")
    public RestaurantInfo getRestaurantInfo(@PathVariable("restaurantName") String restaurantName){
        return restaurantService.getRestaurantInfo(restaurantName);
    }
}
