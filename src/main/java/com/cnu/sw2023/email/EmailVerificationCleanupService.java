package com.cnu.sw2023.email;

import com.cnu.sw2023.email.repository.EmailVerificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailVerificationCleanupService {

    private final EmailVerificationRepository emailVerificationRepository;

    @Transactional
    @Scheduled(fixedRate = 60000)
    public void cleanupExpiredEmailVerifications() {
        Date currentTime = new Date();
        emailVerificationRepository.deleteByExpirationDateBefore(currentTime);
    }
}
