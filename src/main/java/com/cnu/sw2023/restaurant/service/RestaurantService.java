package com.cnu.sw2023.restaurant.service;


import com.cnu.sw2023.restaurant.domain.Restaurant;
import com.cnu.sw2023.restaurant.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final Logger logger = LoggerFactory.getLogger(getClass());


    public boolean findRestaurantByRestaurantName(String restaurantName){
        return restaurantRepository.existsRestaurantByRestaurantName(restaurantName);
    }

    public void saveRestaurantInfo(Map<String, String> restaurantInfoMap) {
        for (Map.Entry<String, String> entry : restaurantInfoMap.entrySet()) {
            String restaurantName = entry.getKey();
            String restaurantId = entry.getValue();

            Restaurant restaurant = new Restaurant();
            restaurant.setRestaurantName(restaurantName);
            restaurant.setRestaurantId(restaurantId);
            restaurantRepository.save(restaurant);
        }
    }

//    private final KakaoApiUtil kakaoApiUtil;
//    @Autowired
//    public void processRestaurantInfo() {
//        Map<String, String> restaurantInfo = kakaoApiUtil.getRestaurantInfo();
//        saveRestaurantInfo(restaurantInfo);
//    }
}