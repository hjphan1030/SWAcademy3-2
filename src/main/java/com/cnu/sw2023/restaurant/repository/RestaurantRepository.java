package com.cnu.sw2023.restaurant.repository;

import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.restaurant.domain.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

     List<Restaurant> findTop3ByOrderByIdDesc();

     Restaurant findByRestaurantName (String restaurant_name);
     boolean existsRestaurantByRestaurantName (String restaurant_name);

     List<Restaurant> findAll ();

}
