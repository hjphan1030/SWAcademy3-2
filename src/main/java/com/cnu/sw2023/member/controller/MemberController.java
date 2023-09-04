package com.cnu.sw2023.member.controller;

import com.cnu.sw2023.member.DTO.JoinReqDto;
import com.cnu.sw2023.member.DTO.LoginRequestDto;
import com.cnu.sw2023.member.service.MemberService;
import com.cnu.sw2023.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    public Page<Post> getMyPosts(@RequestParam int page,Authentication authentication) {
        String email = authentication.getName();
        return memberService.findMyPosts(email,page);
    }
}
