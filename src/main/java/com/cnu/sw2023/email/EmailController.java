package com.cnu.sw2023.email;

import com.cnu.sw2023.member.DTO.JoinReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/join")
@RestController
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/mailConfirm")
    public String mailConfirm(JoinReqDto joinReqDto, Model model) throws MessagingException, UnsupportedEncodingException {

        String authCode = emailService.sendEmail(joinReqDto.getEmail());
        return authCode;
    }
    @PostMapping("/emailCheck")
    public boolean emailVerification(@RequestBody JoinReqDto joinReqDto){
        EmailCheck emailCheck = new EmailCheck();
        emailCheck.setEmail(joinReqDto.getEmail());
        emailCheck.setAuthCode(joinReqDto.getAuthCode());
        return emailService.emailVerification(emailCheck);
    }
}
