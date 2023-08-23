package com.cnu.sw2023;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Sw2023Application {

    public static void main(String[] args) {
        SpringApplication.run(Sw2023Application.class, args);
    }

}
