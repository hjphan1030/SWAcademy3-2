package com.cnu.sw2023.post.controller;

import com.cnu.sw2023.comment.domain.Comment;
import com.cnu.sw2023.exception.PostNotFoundException;
import com.cnu.sw2023.exception.UnauthorizedAccessException;
import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.post.dto.PostUpdateForm;
import com.cnu.sw2023.post.form.DetailPostForm;
import com.cnu.sw2023.post.form.PostForm;
import com.cnu.sw2023.post.dto.PostPageDto;
import com.cnu.sw2023.post.service.PostService;
import com.cnu.sw2023.restaurant.domain.Restaurant;
import com.cnu.sw2023.restaurant.service.RestaurantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@AllArgsConstructor
@RequestMapping("/boards")
@Api(tags = "게시판&음식점 컨트롤러", description = "")
public class PostController {
    private final PostService postService;
    private final RestaurantService restaurantService;


//    @GetMapping("/{restaurant_id}")
//    public ResponseEntity<List<Post>> getPost(@PathVariable String restaurant_id){
//        List<Post> posts = postService.getPostByRestaurantId(restaurant_id);
//        return ResponseEntity.ok(posts);
//    }

    @ApiOperation("특정 음식점 게시판에 글쓰기")
    @PostMapping("/{restaurantName}/post")
    public ResponseEntity<Map<String, Object>> addPost(@RequestBody PostForm postForm
                                                        , @PathVariable("restaurantName") String restaurantName
                                                        , Authentication authentication) {

        Map<String , Object> response = new HashMap<>();
        if ( ! restaurantService.findRestaurantByRestaurantName(restaurantName)) {
                response.put("success",false);
                response.put("location","");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Long postId = postService.addPost(restaurantName, postForm,authentication);
        URI locationUri = URI.create("/boards/posts/" + postId);
        response.put("success",true);
        response.put("location",locationUri);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @ApiOperation("특정 음식점 게시판 리스트 조회")
    @GetMapping("/{restaurantName}")
    public ResponseEntity<Map<String ,Object>> getPostsByRestaurantName(@PathVariable String restaurantName, @RequestParam(defaultValue = "0") int pageNum){
        final int size = 10;

        Pageable pageable = PageRequest.of(pageNum, size, Sort.by("createdAt").descending());
        Page<Post> page = postService.getPostsByRestaurantName(restaurantName, pageable);
        boolean lastPage = page.isLast();
        Stream<PostPageDto> dtoStream = page.stream().map(PostPageDto::new);

        Map<String, Object> response = new HashMap<>();
        response.put("posts", dtoStream.collect(Collectors.toList()));
        response.put("lastPage", lastPage);
        return ResponseEntity.ok().body(response);

    }

    @ApiOperation("특정 게시글 상세 보기")
    @GetMapping("")
    public ResponseEntity<Map<String,Object>> getDetailPost(@RequestParam("postId") Long postId){
        Map<String,Object> res = new HashMap<>();
        Optional<Post> targetPost = postService.getPostByPostId(postId);
        if (targetPost.isEmpty()) { res.put("success",false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }

        Post post = targetPost.get();
        DetailPostForm detailPostForm = new DetailPostForm(post);

        return ResponseEntity.status(HttpStatus.OK).body(detailPostForm.toMap());
    }

    @ApiOperation("특정 게시글 삭제")
    @DeleteMapping("")
    public ResponseEntity<Map<String, Object>> deletePost(
            @RequestParam("postId") Long postId
            ,Authentication authentication){
        String email = authentication.getName();
        Map<String, Object> res = new HashMap<>();
        try {
            postService.deletePost(postId,email);
            res.put("success",true);
            res.put("message","삭제 완료");
            return ResponseEntity.ok().body(res);
        } catch (PostNotFoundException e){
            res.put("success",false);
            res.put("message",e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }catch (UnauthorizedAccessException e){
            res.put("success",false);
            res.put("message",e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(res);
        }
    }

    @ApiOperation("음식점 오름차순 정렬 리스트")
    @GetMapping("/restaurantList")
    public ResponseEntity<Map<String, Object>> getRestaurantList(){
        List<Restaurant> restaurants = restaurantService.findRestaurants();
        Map<String,Object> res = new HashMap<>();

        List<String> restaurantList = restaurants.stream().map(Restaurant::getRestaurantName).collect(Collectors.toList());
        Collections.sort(restaurantList);
        res.put("restaurantList",restaurantList);

        return ResponseEntity.ok().body(res);
    }




    @GetMapping("/checkPostAuth")
    public ResponseEntity<Map<String,String>> checkUpdateAuth(@RequestParam Long id,Authentication authentication){
        String email = authentication.getName();
        Map<String,String> res = new HashMap<>();
        if (postService.checkAuth(id, email)) {
            res.put("message","게시글 수정 권한 있음");
            return ResponseEntity.ok().body(res);
        } else {
            res.put("message","게시글 수정 권한 없음");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(res);
        }
    }
//    @PostMapping("/{postId}/update")
//    public ResponseEntity<Map<String,String>> updatePost(@PathVariable Long postId, @RequestBody PostUpdateForm postUpdateForm){
//        String content = postUpdateForm.getContent();
//        postService.updatePost(postId,content);
//        Map<String, String> res = new HashMap<>();
//        res.put("message","수정 완료");
//        return ResponseEntity.ok().body(res);
//    }
}
