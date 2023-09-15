package com.cnu.sw2023.member.controller;

import com.cnu.sw2023.comment.Form.CommentPageForm;
import com.cnu.sw2023.comment.domain.Comment;
import com.cnu.sw2023.email.EmailCheck;
import com.cnu.sw2023.email.EmailService;
import com.cnu.sw2023.member.DTO.FindIdForm;
import com.cnu.sw2023.member.DTO.JoinReqDto;
import com.cnu.sw2023.member.DTO.LoginRequestDto;
import com.cnu.sw2023.member.exception.DuplicatedException;
import com.cnu.sw2023.member.service.MemberService;
import com.cnu.sw2023.post.domain.Post;
import com.cnu.sw2023.post.dto.PostPageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody JoinReqDto joinReqDto){
        String res = null;
        try {
            res = memberService.join(joinReqDto);
        }catch (Exception e){
            res = e.getMessage();
        }
        return ResponseEntity.ok().body(res);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto){
        String res = null;
        try {
            res = memberService.login(loginRequestDto.getMemberId(),loginRequestDto.getPassword());
            log.info("로그인성공");
            return ResponseEntity.ok().body(res);
        }catch (Exception e){
            res = e.getMessage();
            log.info("로그인실패");
            return ResponseEntity.ok().body(res);
        }
    }

    @GetMapping("/temp")
    public ResponseEntity<String> temp(){ // front 임시 토큰 발급용 api
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

    @PostMapping("/findId")
    public ResponseEntity<Map<String,String>> findMyId(@RequestBody FindIdForm findIdForm){
        String email = findIdForm.getEmail();
        HashMap<String, String> res = new HashMap<>();
        if (memberService.existingEmail(email)) {
            res.put("message", "이메일로 아이디가 발송되었습니다");
            return ResponseEntity.ok().body(res);
        } else {
            res.put("message","존재하지 않는 이메일입니다");
            return ResponseEntity.ok().body(res);
        }
    }

//    @PostMapping("/findPassword")
//    public ResponseEntity<Map<String,String>> findMyPassword(@RequestBody FindPasswordForm passwordForm){
//
//    }
}
