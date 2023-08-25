package com.cnu.sw2023.comment.controller;

import com.cnu.sw2023.comment.Form.CommentForm;
import com.cnu.sw2023.comment.service.CommentService;
import lombok.AllArgsConstructor;
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
}
