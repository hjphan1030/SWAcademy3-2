package com.cnu.sw2023.hj.controller;

import com.cnu.sw2023.comment.Form.CommentForm;
import com.cnu.sw2023.comment.domain.Comment;
import com.cnu.sw2023.comment.domain.CommentUpdateForm;
import com.cnu.sw2023.comment.service.CommentService;
import com.cnu.sw2023.hj.service.PostWriteService;
import com.cnu.sw2023.like.service.LikeService;
import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.post.form.DetailPostForm;
import com.cnu.sw2023.post.form.PostForm;
import com.cnu.sw2023.post.form.UpdatePostForm;
import com.cnu.sw2023.post.service.PostService;
import com.cnu.sw2023.restaurant.domain.Restaurant;
import com.cnu.sw2023.restaurant.service.RestaurantService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PostWriteController {

    private final PostService postService;
    private final PostWriteService postWriteService;
    private final RestaurantService restaurantService;
    private final CommentService commentService;
    private final LikeService likeService;


    @GetMapping("/boards/post")
    public String showPostForm(Model model) {
//        String token = memberService.temp();
//        System.out.println(token);
//        request.setAttribute("Authorization", "Bearer " + token);

//        if ( ! authentication.isAuthenticated())
//            return "pageFault";
        List<Restaurant> restaurantList = restaurantService.findRestaurants();
        model.addAttribute("restaurantList", restaurantList);
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
        Post view = postWriteService.showPost(postId);
        List<Comment> commentList = commentService.getCommentList(postId);
        model.addAttribute("commentList", commentList);
        model.addAttribute("view", view);
//        model.addAttribute("userName", authentication.getName());

        return "detailPost";
    }

    @ApiOperation("게시글 수정")
    @GetMapping("/boards/{postId}/update")
    public String showUpdatePostForm(@PathVariable Long postId, Model model) {
        Post view = postWriteService.showPost(postId);
        List<Comment> commentList = commentService.getCommentList(postId);
        List<Restaurant> restaurantList = restaurantService.findRestaurants();
        model.addAttribute("restaurantList", restaurantList);
        model.addAttribute("commentList", commentList);
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
        Post view = postWriteService.showPost(postId);
        List<Comment> commentList = commentService.getCommentList(postId);
        model.addAttribute("commentList", commentList);
        model.addAttribute("view", view);

        return "detailPost";
    }

    @ApiOperation("게시글 삭제")
    @GetMapping("/boards/{postId}/delete")
    public String deletePost(@PathVariable Long postId) {
        postWriteService.deletePost(postId);
        return "postList";
    }

    @ApiOperation("댓글 작성하기")
    @PostMapping("/boards/{postId}/comment")
    public String writeComment(@PathVariable Long postId, String commentContent, Model model) {
        if (commentContent == null) {
            return "pageFault";
        }
        CommentForm commentForm = new CommentForm();
        commentForm.setPostId(postId);
        commentForm.setContent(commentContent);
        Comment commentView = commentService.postComment(commentForm);
        Post view = postWriteService.showPost(postId);
        List<Comment> commentList = commentService.getCommentList(postId);
        model.addAttribute("commentList", commentList);
        model.addAttribute("commentView", commentView);
        model.addAttribute("view", view);
        return "detailPost";
    }

    @ApiOperation("댓글 수정하기")
    @GetMapping("/boards/comment/{commentId}/update")
    public String showUpdateCommentForm(@PathVariable Long commentId, Model model) {
        Long postId = commentService.getPostId(commentId);
        Post view = postWriteService.showPost(postId);
        Comment commentView = commentService.showComment(commentId);
        List<Comment> commentList = commentService.getCommentList(postId);
        model.addAttribute("commentList", commentList);
        model.addAttribute("view", view);
        model.addAttribute("commentView", commentView);

        return "updateComment";
    }

    @ApiOperation("댓글 수정하기")
    @PostMapping("/boards/comment/{commentId}/update")
    public String updateComment(CommentUpdateForm commentUpdateForm, @PathVariable Long commentId, Model model) {
        String commentContent = commentUpdateForm.getContent();
        if (commentContent == null) {
            return "pageFault";
        }
        Long postId = commentService.getPostId(commentId);
        Post view = postWriteService.showPost(postId);
        Comment commentView = commentService.updateComment(commentId, commentContent);
        List<Comment> commentList = commentService.getCommentList(postId);
        model.addAttribute("commentList", commentList);
        model.addAttribute("commentView", commentView);
        model.addAttribute("view", view);
        return "detailPost";
    }

    @ApiOperation("댓글 삭제하기")
    @GetMapping("/boards/comment/{commentId}/delete")
    public String deleteComment(@PathVariable Long commentId, Model model) {
        Long postId = commentService.getPostId(commentId);
        Post view = postWriteService.showPost(postId);
        commentService.deleteComment(commentId);
        List<Comment> commentList = commentService.getCommentList(postId);
        model.addAttribute("commentList", commentList);
        model.addAttribute("view", view);
        return "detailPost";
    }

    @ApiOperation("게시글 좋아요 누르기")
    @PostMapping("/boards/post/{postId}/like")
    public String postLike(@PathVariable Long postId, Model model) {
        likeService.postLike(postId, "temp123@naver.com");
        List<Comment> commentList = commentService.getCommentList(postId);
        Post view = postWriteService.showPost(postId);
        model.addAttribute("view", view);
        model.addAttribute("commentList", commentList);
        return "detailPost";
    }

    @ApiOperation("댓글 좋아요 누르기")
    @PostMapping("/boards/comment/{commentId}/like")
    public String commentLike(@PathVariable Long commentId, Model model) {
        likeService.commentLike(commentId, "temp123@naver.com");

        Long postId = commentService.getPostId(commentId);
        List<Comment> commentList = commentService.getCommentList(postId);
        Post view = postWriteService.showPost(postId);
        model.addAttribute("view", view);
        model.addAttribute("commentList", commentList);
        return "detailPost";
    }

    @ApiOperation("게시글 상세 보기")
    @GetMapping("/boards/post/{postId}/detail")
    public String showDetailPost(@PathVariable Long postId, Model model) {
        Optional<Post> targetPost = postService.getPostByPostId(postId);
        if (targetPost.isEmpty()) {
            return "pageFault";
        }

        Post post = targetPost.get();
        DetailPostForm detailPostForm = new DetailPostForm(post);
        Post view = postWriteService.showPost(postId);
        List<Comment> commentList = commentService.getCommentList(postId);
        model.addAttribute("view", view);
        model.addAttribute("commentList", commentList);
        return "detailPost";
    }

//    @ApiOperation("게시판 목록 불러오기")
//    @GetMapping
}

