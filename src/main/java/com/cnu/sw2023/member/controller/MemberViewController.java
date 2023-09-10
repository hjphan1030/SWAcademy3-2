package com.cnu.sw2023.member.controller;

import com.cnu.sw2023.member.DTO.JoinReqDto;
import com.cnu.sw2023.member.DTO.LoginRequestDto;
import com.cnu.sw2023.member.domain.Member;
import com.cnu.sw2023.member.exception.NotFoundException;
import com.cnu.sw2023.member.exception.UnMatchedPasswordException;
import com.cnu.sw2023.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

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
        String result = memberService.join(joinReqDto);
        if (result.equals("회원가입 성공")) {
            return "redirect:/user/login";
        } else {
            bindingResult.rejectValue("email", "emailDuplicated", "이미 존재하는 이메일입니다");
            return "joinForm";
        }
    }

    @GetMapping("/login")
    public String showLoginForm(LoginRequestDto loginRequestDto){
        log.info("로그인 페이지에 들어왔습니다");
        return "loginForm";
    }
    @PostMapping("/login")
    public String processLoginForm(LoginRequestDto loginRequestDto, HttpServletRequest request,Model model
            ,BindingResult bindingResult) {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();
        try {
            String token = memberService.login(email, password);
            if (token != null) {
                model.addAttribute("Authorization","Bearer "+token);
            }
            return "index";
        } catch (UnMatchedPasswordException e) {
            String errorMessage = e.getMessage();
            bindingResult.rejectValue("password","passwordInCorrect","비밀번호가 일치하지 않습니다");
            return "loginForm";
        }catch (NotFoundException e){
            String errorMessage = e.getMessage();
            bindingResult.rejectValue("email","notFoundEmail","존재하지 않는 이메일입니다");
            return "loginForm";
        }
    }

    @GetMapping("/index")
    public String successLogin(HttpServletRequest request){
        return "index";
    }
}
