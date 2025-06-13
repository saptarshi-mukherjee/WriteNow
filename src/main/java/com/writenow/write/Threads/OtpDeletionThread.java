package com.writenow.write.Threads;

import com.writenow.write.Models.EmailValidation;
import com.writenow.write.Repositories.EmailValidationRepository;

import java.time.LocalDateTime;
import java.util.List;

public class OtpDeletionThread implements Runnable {

    private EmailValidationRepository emailValidationRepository;
    private Object lock;

    public OtpDeletionThread(EmailValidationRepository emailValidationRepository, Object lock) {
        this.emailValidationRepository = emailValidationRepository;
        this.lock=lock;
    }

    @Override
    public void run() {
        List<EmailValidation> validations=emailValidationRepository.fetchAllOtp();
        for(EmailValidation ev : validations) {
            synchronized (lock) {
                if (ev.getExpiry().isBefore(LocalDateTime.now()))
                    emailValidationRepository.delete(ev);
            }
        }
    }
}
