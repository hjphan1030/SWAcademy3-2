package com.cnu.sw2023.hj.controller;

import com.cnu.sw2023.exception.PostNotFoundException;
import com.cnu.sw2023.exception.UnauthorizedAccessException;
import com.cnu.sw2023.hj.service.PostWriteService;
import com.cnu.sw2023.member.service.MemberService;
import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.post.form.PostForm;
import com.cnu.sw2023.post.form.UpdatePostForm;
import com.cnu.sw2023.post.repository.PostRepository;
import com.cnu.sw2023.post.service.PostService;
import com.cnu.sw2023.restaurant.service.RestaurantService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    private final MemberService memberService;
    private final PostRepository postRepository;


    @GetMapping("/boards/post")
    public String showPostForm() {
//        String token = memberService.temp();
//        System.out.println(token);
//        request.setAttribute("Authorization", "Bearer " + token);

//        if ( ! authentication.isAuthenticated())
//            return "pageFault";

        return "postWrite";
    }

    @ApiOperation("게시글 작성하기")
    @PostMapping("/boards/post")
    public String postWrite(String title, String content, String restaurantName, Model model) {

        if (title == null) {
            return "pageFault";
        }
        if (content == null) {
            return "pageFault";
        }
        if (restaurantName == null) {
            return "pageFault";
        }
        if (!restaurantService.findRestaurantByRestaurantName(restaurantName))
            return "pageFault";

        PostForm postForm = new PostForm();
        postForm.setTitle(title);
        postForm.setContent(content);
        Long postId = postWriteService.writePost(postForm, restaurantName);
//        postService.addPost(restaurantName, postForm1);
        model.addAttribute("title", postForm.getTitle());
        model.addAttribute("content", postForm.getContent());
        model.addAttribute("restaurantName", restaurantName);
        model.addAttribute("postId", postId);
//        model.addAttribute("userName", authentication.getName());

        return "detailPost";
    }

    @ApiOperation("게시글 수정")
    @GetMapping("/boards/{postId}/update")
    public String showUpdatePostForm(@PathVariable Long postId, Model model) {
        Post view = postWriteService.showPost(postId);
        model.addAttribute("view", view);
        model.addAttribute("postId", postId);

        return "updatePost";
    }

    @ApiOperation("게시글 수정")
    @PostMapping("/boards/{postId}/update")
    public String updatePost(UpdatePostForm updatePostForm, @PathVariable Long postId, Model model) {

        String title = updatePostForm.getTitle();
        String content = updatePostForm.getContent();
        String restaurantName = updatePostForm.getRestaurantName();

        if (title == null) {
            return "pageFault";
        }
        if (content == null) {
            return "pageFault";
        }
        if (restaurantName == null) {
            return "pageFault";
        }
        if (!restaurantService.findRestaurantByRestaurantName(restaurantName))
            return "pageFault";

        postWriteService.updatePost(postId, title, content, restaurantName);
        model.addAttribute("title", title);
        model.addAttribute("content", content);
        model.addAttribute("restaurantName", restaurantName);

        return "detailPost";
    }

    @ApiOperation("게시글 삭제")
    @GetMapping("/boards/{postId}/delete")
    public String deletePost(@PathVariable Long postId) {
        postWriteService.deletePost(postId);
        return "postList";
    }
}

