package com.cnu.sw2023.member.controller;

import com.cnu.sw2023.member.DTO.MemberJoinDto;
import com.cnu.sw2023.member.DTO.MemberLoginDto;
import com.cnu.sw2023.member.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @PostMapping("/join")
    public ResponseEntity<String> createMember(@RequestBody MemberJoinDto memberJoinDto){
        memberService.createMember(memberJoinDto);
        return ResponseEntity.ok().body("회원 가입이 성공 했습니다");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody MemberLoginDto memberLoginDto){
        String token = memberService.login(memberLoginDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+token);

        return ResponseEntity.ok().headers(headers).body("로그인 성공");
    }
}
