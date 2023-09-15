package com.cnu.sw2023.email.repository;

import com.cnu.sw2023.email.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {

    boolean existsByEmailAndAuthCode(String email, String authCode);

    void deleteByExpirationDateBefore(Date expirationDate);
}
