package com.cnu.sw2023.comment.controller;

import com.cnu.sw2023.comment.Form.CommentForm;
import com.cnu.sw2023.comment.domain.CommentUpdateForm;
import com.cnu.sw2023.comment.service.CommentService;
import com.cnu.sw2023.post.dto.PostUpdateForm;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/boards/comment")
    public Map<String, Object> postComment (@RequestBody @Valid CommentForm commentForm , BindingResult bindingResult){
         Map<String, Object> res = new HashMap<>();
        if (bindingResult.hasErrors()) {
            res.put("success",false);
            res.put("message","내용은 1자 이상");
            return res;
        }
        commentService.postComment(commentForm);
        res.put("success",true);
        res.put("message","작성완료");
        return res;
    }

    @GetMapping("/checkCommentAuth")
    public ResponseEntity<Map<String,String>> checkUpdateAuth(@RequestParam Long id, Authentication authentication){
        String email = authentication.getName();
        Map<String,String> res = new HashMap<>();
        if (commentService.checkAuth(id, email)) {
            res.put("message","댓글 수정 권한 있음");
            return ResponseEntity.ok().body(res);
        } else {
            res.put("message","댓글 수정 권한 없음");
            return ResponseEntity.ok().body(res);
        }
    }

    @PostMapping("/{commentId}/update")
    public ResponseEntity<Map<String,String>> updatePost(@PathVariable Long commentId, @RequestBody CommentUpdateForm commentUpdateForm){
        String content = commentUpdateForm.getContent();
        commentService.updateComment(commentId,content);
        Map<String, String> res = new HashMap<>();
        res.put("message","수정 완료");
        return ResponseEntity.ok().body(res);
    }

//    @GetMapping("/getMyComment")
//    public Page<>
}
