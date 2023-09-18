package com.cnu.sw2023.config.jwtconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EncoderConfig {
    @Bean
    public BCryptPasswordEncoder encoder() { // securityConfig 에 같이 넣으면 순환참조 오류 생길 수 도 있음
        return new BCryptPasswordEncoder();
    }
}
