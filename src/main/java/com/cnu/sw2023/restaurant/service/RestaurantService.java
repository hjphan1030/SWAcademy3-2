package com.cnu.sw2023.restaurant.service;

import com.cnu.sw2023.config.kakaoApi.KakaoApiUtil;
import com.cnu.sw2023.restaurant.domain.Restaurant;
import com.cnu.sw2023.restaurant.repository.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RestaurantService {

    private final KakaoApiUtil kakaoApiUtil;
    private final RestaurantRepository restaurantRepository;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public RestaurantService(KakaoApiUtil kakaoApiUtil, RestaurantRepository restaurantRepository) {
        this.kakaoApiUtil = kakaoApiUtil;
        this.restaurantRepository = restaurantRepository;
    }

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

    @Autowired
    public void processRestaurantInfo() {
        Map<String, String> restaurantInfo = kakaoApiUtil.getRestaurantInfo();
        saveRestaurantInfo(restaurantInfo);
    }
}
