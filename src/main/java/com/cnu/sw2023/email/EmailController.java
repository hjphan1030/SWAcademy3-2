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
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/mailConfirm")
    public ResponseEntity<Map<String,String>> mailConfirm(@RequestBody @Valid EmailConfirmDto emailConfirmDto){
        String email = emailConfirmDto.getEmail();
        log.info(email);
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
    public ResponseEntity<Map<String,String>> emailVerification(@RequestBody EmailCheck emailCheck){
        boolean result = emailService.emailVerification(emailCheck);
        HashMap<String, String> res = new HashMap<>();
        res.put("success",String.valueOf(result));
        return ResponseEntity.ok().body(res);
    }
}
