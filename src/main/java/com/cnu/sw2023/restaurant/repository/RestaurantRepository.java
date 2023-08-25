package com.cnu.sw2023.restaurant.repository;

import com.cnu.sw2023.restaurant.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

     Restaurant findByRestaurantName (String restaurant_name);
     boolean existsRestaurantByRestaurantName (String restaurant_name);

     List<Restaurant> findAll ();
}
