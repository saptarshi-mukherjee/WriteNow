package com.writenow.write.Threads;

import com.writenow.write.Helpers.EmailHelper;
import com.writenow.write.Strategies.OtpGeneration.OtpGenerationStrategy;
import org.springframework.mail.javamail.JavaMailSender;

public class OtpGenerationThread implements Runnable {

    private OtpGenerationStrategy strategy;
    private JavaMailSender mailSender;
    private String email;
    private Object lock;

    public OtpGenerationThread(OtpGenerationStrategy strategy, JavaMailSender mailSender, String email, Object lock) {
        this.strategy = strategy;
        this.mailSender = mailSender;
        this.email = email;
        this.lock=lock;
    }

    @Override
    public void run() {
        int otp=-1;
        synchronized (lock) {
            otp= strategy.generateOtp(email);
        }
        synchronized (lock) {
            EmailHelper.sendEmail(mailSender,email,otp);
        }
    }
}
