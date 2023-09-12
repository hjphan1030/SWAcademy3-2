package com.cnu.sw2023.member.controller;

import com.cnu.sw2023.member.DTO.JoinReqDto;
import com.cnu.sw2023.member.DTO.LoginRequestDto;
import com.cnu.sw2023.member.exception.NotFoundException;
import com.cnu.sw2023.member.exception.UnMatchedPasswordException;
import com.cnu.sw2023.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class MemberViewController {
    private final MemberService memberService;
    @GetMapping("/join")
    public String joinForm(JoinReqDto joinReqDto) {
        return "joinForm";
    }
    @PostMapping("/join")
    public String processJoinForm(@Valid JoinReqDto joinReqDto
            ,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "joinForm";
        }
        if (!joinReqDto.getPassword().equals(joinReqDto.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "joinForm";
        }
        try {
            String result = memberService.join(joinReqDto);
            return "redirect:/user/login";
        }catch (Exception e){
            bindingResult.reject("duplicatedError", e.getMessage());
            return "joinForm";
        }
    }

    @GetMapping("/login")
    public String showLoginForm(LoginRequestDto loginRequestDto){
        log.info("로그인 페이지에 들어왔습니다");
        return "loginForm";
    }
    @PostMapping("/login")
    public String processLoginForm(LoginRequestDto loginRequestDto
            ,Model model
            ,BindingResult bindingResult) {
        String memberId = loginRequestDto.getMemberId();
        String password = loginRequestDto.getPassword();
        try {
            String token = memberService.login(memberId, password);
            if (token != null) {
                model.addAttribute("Authorization","Bearer "+token);
            }
            return "index";
        } catch (UnMatchedPasswordException e) {
            bindingResult.rejectValue("password","passwordInCorrect",e.getMessage());
            return "loginForm";
        }catch (NotFoundException e){
            bindingResult.rejectValue("memberId","notFoundMemberId",e.getMessage());
            return "loginForm";
        }
    }

    @GetMapping("/index")
    public String successLogin(HttpServletRequest request){
        return "index";
    }

    @GetMapping("/test")
    public String test(){
        return "passwordFindForm";
    }
}
