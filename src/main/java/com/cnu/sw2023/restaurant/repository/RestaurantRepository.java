package com.cnu.sw2023.restaurant.repository;

import com.cnu.sw2023.member.domain.College;
import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.restaurant.domain.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

     List<Restaurant> findTop3ByOrderByIdDesc();

     Restaurant findByRestaurantName (String restaurant_name);
     boolean existsRestaurantByRestaurantName (String restaurant_name);

     List<Restaurant> findAll ();

     List<Restaurant> findByRestaurantNameContaining(String keyword);

     @Query("SELECT r FROM Restaurant r WHERE (:category is null OR r.category = :category) AND (:region is null OR r.region = :region)")
     List<Restaurant> findRestaurantsByCategoryAndRegion(@Param("category") String category, @Param("region") String region);

     @Query("SELECT r FROM Restaurant r LEFT JOIN Post p ON r.restaurantName = p.restaurant.restaurantName " +
             "LEFT JOIN Member m ON p.email = m.email AND m.college = :college " +
             "WHERE r.restaurantName IN :restaurantNames " +
             "GROUP BY r.restaurantName " +
             "ORDER BY COUNT(p.id) DESC")
     List<Restaurant> findRestaurantsByCollegeWithMostPosts(@Param("college") College college, @Param("restaurantNames") List<String> restaurantNames);

}
