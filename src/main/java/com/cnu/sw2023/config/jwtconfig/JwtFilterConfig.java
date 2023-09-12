package com.cnu.sw2023.config.jwtconfig;

import com.cnu.sw2023.member.service.MemberService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtFilterConfig {
    @Bean
    public JwtFilter jwtFilter(MemberService memberService, @Value("${jwt.secret}") String secretKey) {
        return new JwtFilter(memberService, secretKey);
    }
}