package com.cnu.sw2023.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class EncoderConfig {

    @Bean //securityConfig 에 같이 넣어두면 순환참조 오류 생길 수 있음
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
