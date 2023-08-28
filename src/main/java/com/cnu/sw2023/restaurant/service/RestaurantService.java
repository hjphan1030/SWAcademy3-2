package com.cnu.sw2023.restaurant.service;


import com.cnu.sw2023.config.KakaoApiUtil;
import com.cnu.sw2023.restaurant.domain.Restaurant;
import com.cnu.sw2023.restaurant.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final Logger logger = LoggerFactory.getLogger(getClass());




    public boolean findRestaurantByRestaurantName(String restaurantName){
        return restaurantRepository.existsRestaurantByRestaurantName(restaurantName);
    }

    public void saveRestaurantInfo(Map<String, List<Object>> restaurantInfoMap) {
        for (Map.Entry<String, List<Object>> entry : restaurantInfoMap.entrySet()) {
            List<Object> list = entry.getValue();
            String addressName = (String) list.get(0);
            String id = (String) list.get(1);
            String phone = (String) list.get(2);
            String placeName = (String) list.get(3);
            String category = (String) list.get(4);

            Restaurant restaurant = new Restaurant();
            restaurant.setRestaurantId(id);
            restaurant.setRestaurantName(placeName);
            restaurant.setPhone(phone);
            restaurant.setAddressName(addressName);
            restaurant.setCategory(category);

            restaurantRepository.save(restaurant);
        }
    }
    public List<Restaurant> findRestaurants(){
        return restaurantRepository.findAll();
    }

    private final KakaoApiUtil kakaoApiUtil;
//    @Autowired
    public void processRestaurantInfo() {
        Map<String, List<Object>> restaurantInfo = kakaoApiUtil.getRestaurantInfo();
        saveRestaurantInfo(restaurantInfo);
    }

    public Map<String,String > getRestaurantInfo(String restaurantName) {
        Restaurant restaurant = restaurantRepository.findByRestaurantName(restaurantName);
        Map<String, String> res = new HashMap<>();
        res.put("address",restaurant.getAddressName());
        res.put("phone",restaurant.getPhone());
        return res;
    }
}
