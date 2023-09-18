package com.cnu.sw2023.member.controller;

import com.cnu.sw2023.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class TempTokenController {

    private final MemberService memberService;

    @GetMapping("/tempToken")
    public String tempToken(HttpServletRequest request){
        String token = memberService.temp();
        request.setAttribute("Authorization", "Bearer " + token);
        return "index11";
    }
}
