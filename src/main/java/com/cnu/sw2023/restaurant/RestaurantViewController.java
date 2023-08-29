//package com.cnu.sw2023.restaurant;
//
//import com.cnu.sw2023.post.domain.Post;
//import com.cnu.sw2023.post.dto.PostPageDto;
//import com.cnu.sw2023.post.service.PostService;
//import com.cnu.sw2023.restaurant.domain.Restaurant;
//import com.cnu.sw2023.restaurant.service.RestaurantService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/test")
//public class RestaurantViewController {
//    private final PostService postService;
//    private final RestaurantService restaurantService;
//
//
//    @GetMapping("/restaurantList")
//    public String restaurantPage(Model model) {
//        List<Restaurant> restaurants = restaurantService.findRestaurants();
//        List<String> restaurantNames = restaurants.stream().map(Restaurant::getRestaurantName).collect(Collectors.toList());
//        Collections.sort(restaurantNames);
//
//        model.addAttribute("restaurantList", restaurantNames);
//
//        return "restaurantList";
//    }
//
//    @GetMapping("/{restaurantName}/")
//    public String getPostsForRestaurant(@PathVariable String restaurantName, Model model, @RequestParam(defaultValue = "0") int pageNum) {
//        Pageable pageable = PageRequest.of(pageNum, 10, Sort.by("createdAt").descending());
//        Page<Post> postPage = postService.getPostsByRestaurantName(restaurantName, pageable);
//
//        List<PostPageDto> postDtoList = postPage.stream().map(PostPageDto::new).collect(Collectors.toList());
//
//        model.addAttribute("selectedRestaurantName", restaurantName);
//        model.addAttribute("posts", postDtoList);
//
//        return "restaurantList";
//    }
//}
