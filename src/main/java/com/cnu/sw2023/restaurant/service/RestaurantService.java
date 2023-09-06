package com.cnu.sw2023.restaurant.service;


import com.cnu.sw2023.config.KakaoApiUtil;
import com.cnu.sw2023.restaurant.domain.Restaurant;
import com.cnu.sw2023.restaurant.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RestaurantService {

    @PersistenceContext
    private EntityManager entityManager;

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
            String region = (String) list.get(5);

            Restaurant restaurant = new Restaurant();
            restaurant.setRestaurantId(id);
            restaurant.setRestaurantName(placeName);
            restaurant.setPhone(phone);
            restaurant.setAddressName(addressName);
            restaurant.setCategory(category);
            restaurant.setRegion(region);
            restaurantRepository.save(restaurant);

        }
    }
    public List<Restaurant> findRestaurants(){
        return restaurantRepository.findAll();
    }

    private final KakaoApiUtil kakaoApiUtil;
    @Autowired
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

    public List<Restaurant> getRankingByCategoryAndCollege(String category, String college) {
        String jpql = "select p.restaurant, COUNT(p) " +
                "from Post p " +
                "where p.restaurant.category LIKE :category " +
                "and p.title = :college " +
                "GROUP BY p.restaurant " +
                "ORDER BY COUNT(p) DESC";

        TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
        query.setParameter("category", "%" + category + "%");

        query.setParameter("college", college);


        List<Object[]> results = query.getResultList();

        Map<Restaurant, Integer> restaurantPostCountMap = new HashMap<>();
        for (Object[] result : results) {
            Restaurant restaurant = (Restaurant) result[0];
            Long postCount = (Long) result[1];
            restaurantPostCountMap.put(restaurant, postCount.intValue());
        }
        return restaurantPostCountMap.entrySet()
                .stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
