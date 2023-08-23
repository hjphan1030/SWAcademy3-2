package com.cnu.sw2023.restaurant.repository;

import com.cnu.sw2023.restaurant.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

     Restaurant findByRestaurantId (String restaurant_id);
     boolean existsRestaurantByRestaurantId (String restaurant_id);
}
