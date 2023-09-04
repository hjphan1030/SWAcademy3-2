package com.cnu.sw2023.hj.controller;

import com.cnu.sw2023.hj.service.PostWriteService;
import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.post.form.PostForm;
import com.cnu.sw2023.post.service.PostService;
import com.cnu.sw2023.restaurant.service.RestaurantService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class PostWriteController {

    private final PostWriteService postWriteService;
    private final PostService postService;
    private final RestaurantService restaurantService;

    @GetMapping("/boards/{restaurantName}/post")
    public String showPostForm(@PathVariable String restaurantName,Model model) {
        model.addAttribute("restaurantName",restaurantName);
        return "postWrite";
    }

    @ApiOperation("게시글 작성하기")
        @PostMapping("/boards/{restaurantName}/post1")
        public String postWrite(String title, String content, @PathVariable("restaurantName") String restaurantName, Model model, Authentication authentication) {
    //        postWriteService.write(title, content);

        if ( ! restaurantService.findRestaurantByRestaurantName(restaurantName))
            return "pageFault";


        if ( ! authentication.isAuthenticated())
            return "pageFault";

        PostForm postForm = new PostForm();
        postForm.setTitle(title);
        postForm.setContent(content);
        postWriteService.writePost(postForm,restaurantName);
//        postService.addPost(restaurantName, postForm1);
        model.addAttribute("title", postForm.getTitle());
        model.addAttribute("content", postForm.getContent());
        model.addAttribute("userName", authentication.getName());
        return "detailPost";
    }
}

