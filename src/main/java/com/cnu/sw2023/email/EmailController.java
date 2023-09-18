package com.cnu.sw2023.email;

import com.cnu.sw2023.exception.EmailException;
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
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/mailConfirm")
    public ResponseEntity<Map<String,String>> mailConfirm(EmailConfirmDto emailConfirmDto) throws EmailException {
        String email = emailConfirmDto.getEmail();
        HashMap<String, String> res = new HashMap<>();
        try {
            String authCode = emailService.sendEmail(email);
            res.put("success","true");
            return ResponseEntity.ok().body(res);
        } catch (MessagingException | UnsupportedEncodingException e) {
            res.put("success","false");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        }
    }

    @PostMapping("/emailCheck")
    public boolean emailVerification(@RequestBody EmailCheck emailCheck){
        String email = emailCheck.getEmail();
        String authCode = emailCheck.getEmail();
        return emailService.emailVerification(emailCheck);
    }
}
