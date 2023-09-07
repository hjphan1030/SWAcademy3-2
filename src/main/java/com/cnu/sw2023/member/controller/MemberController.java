package com.cnu.sw2023.member.controller;

import com.cnu.sw2023.comment.Form.CommentPageForm;
import com.cnu.sw2023.comment.domain.Comment;
import com.cnu.sw2023.member.DTO.JoinReqDto;
import com.cnu.sw2023.member.DTO.LoginRequestDto;
import com.cnu.sw2023.member.service.MemberService;
import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.post.dto.PostPageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody JoinReqDto joinReqDto){

        return ResponseEntity.ok().body(memberService.join(joinReqDto));
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok().body(memberService.login(loginRequestDto.getEmail(),loginRequestDto.getPassword()));
    }

    @GetMapping("/temp")
    public ResponseEntity<String> temp(){ // front 임시 토큰 발급용 api
        HashMap<String, String> res = new HashMap<>();
        return ResponseEntity.ok().body(memberService.temp());
    }
    @GetMapping("/myPosts")
    public List<PostPageDto> getMyPosts(@RequestParam int page, Authentication authentication) {
        String email = authentication.getName();
        Page<Post> pages = memberService.findMyPosts(email, page);
        Stream<PostPageDto> dtoStream = pages.stream().map(PostPageDto::new);
        return dtoStream.collect(Collectors.toList());
    }

    @GetMapping("/myComments")
    public List<CommentPageForm> getMyComments(@RequestParam int page,Authentication authentication){
        String email = authentication.getName();
        Page<Comment> pages = memberService.findMyComments(email, page);
        return pages.stream().map(CommentPageForm::new).collect(Collectors.toList());
    }
}
