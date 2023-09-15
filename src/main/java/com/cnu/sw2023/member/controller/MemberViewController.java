package com.cnu.sw2023.member.controller;

import com.cnu.sw2023.email.EmailService;
import com.cnu.sw2023.member.DTO.JoinReqDto;
import com.cnu.sw2023.member.DTO.LoginRequestDto;
import com.cnu.sw2023.member.exception.NotFoundException;
import com.cnu.sw2023.member.exception.UnMatchedPasswordException;
import com.cnu.sw2023.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class MemberViewController {
    private final MemberService memberService;
    private final EmailService emailService;
    @GetMapping("/join")
    public String joinForm(Model model,JoinReqDto joinReqDto) {
        List<String> collegeList = memberService.getCollegeList();
        log.info("join get !");

        model.addAttribute("collegeList", collegeList);
        return "joinForm";
    }
    @PostMapping("/join")
    public String processJoinForm(@Valid JoinReqDto joinReqDto,BindingResult bindingResult,Model model) {
        model.addAttribute(joinReqDto);
        if (bindingResult.hasErrors()) {
            return "joinForm";
        }
        log.info("hoho");
        List<String> collegeList = memberService.getCollegeList();
        model.addAttribute("collegeList", collegeList);
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
    @PostMapping("/mailConfirm")
    public ModelAndView mailConfirm(Model model) throws MessagingException, UnsupportedEncodingException {
        List<String> collegeList = memberService.getCollegeList();
        model.addAttribute("collegeList", collegeList);
        JoinReqDto joinReqDto = (JoinReqDto) model.getAttribute("joinReqDto");
        model.addAttribute(joinReqDto);
        String email = joinReqDto.getEmail();
        String authCode = emailService.sendEmail(email);

        ModelAndView modelAndView = new ModelAndView("joinForm");
        modelAndView.addObject("authCode", authCode);
        return modelAndView;
    }
    @PostMapping(value = "/api/mailcheck", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> mailCheck(@RequestBody HashMap<String, Object> user) throws MessagingException, UnsupportedEncodingException {

        String username = (String) user.get("username");
        String authNum = emailService.sendEmail(username);

        log.info("email : " + user.get("username"));
        log.info("checkNum : " + authNum);

        return ResponseEntity.status(HttpStatus.OK).body(authNum);
    }

    @GetMapping("/login")
    public String showLoginForm(Model model){
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        model.addAttribute(loginRequestDto);
        log.info("로그인 페이지에 들어왔습니다");
        return "loginForm";
    }

    @PostMapping("/login")
    public String processLoginForm(Model model, LoginRequestDto loginRequestDto
            , BindingResult bindingResult, HttpServletResponse response) {
        Cookie cookie = new Cookie("token","Bearer ");
        String memberId = loginRequestDto.getMemberId();
        String password = loginRequestDto.getPassword();
        try {
            String token = memberService.login(memberId, password);
            if (token != null) {
                model.addAttribute("Authorization","Bearer "+token);
            }
            return "redirect:/";
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
        return "index1";
    }

    @GetMapping("/test")
    public String test(){
        return "testSy";
    }
}
