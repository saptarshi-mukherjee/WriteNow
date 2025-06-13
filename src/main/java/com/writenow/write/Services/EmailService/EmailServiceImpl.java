package com.writenow.write.Services.EmailService;


import com.writenow.write.Models.EmailStatus;
import com.writenow.write.Models.EmailValidation;
import com.writenow.write.Models.User;
import com.writenow.write.Projections.UserIdProjection;
import com.writenow.write.Repositories.EmailValidationRepository;
import com.writenow.write.Repositories.UserRepository;
import com.writenow.write.Strategies.OtpGeneration.OtpGenerationStrategy;
import com.writenow.write.Strategies.OtpGeneration.SecuredRandomStrategy;
import com.writenow.write.Threads.OtpDeletionThread;
import com.writenow.write.Threads.OtpGenerationThread;
import jakarta.annotation.PreDestroy;
import jakarta.transaction.Transactional;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Transactional
public class EmailServiceImpl implements EmailService {

    private JavaMailSender mailSender;
    private EmailValidationRepository emailValidationRepository;
    private ExecutorService exs1, exs2;
    private static final Object lock1=new Object();
    private static final Object lock2=new Object();
    private UserRepository userRepository;

    public EmailServiceImpl(JavaMailSender mailSender, EmailValidationRepository emailValidationRepository, UserRepository userRepository) {
        this.mailSender = mailSender;
        this.emailValidationRepository = emailValidationRepository;
        exs1= Executors.newFixedThreadPool(20);
        exs2=Executors.newFixedThreadPool(5);
        this.userRepository=userRepository;
    }

    @Override
    public void sendEmail(String email) {
        OtpGenerationStrategy strategy=new SecuredRandomStrategy(emailValidationRepository);
        OtpGenerationThread thread=new OtpGenerationThread(strategy,mailSender,email,lock1);
        exs1.submit(thread);
    }

    @Override
    public void checkOtp(String fullName, String email, int otp) throws Exception {
        List<UserIdProjection> userIdProjectionList=userRepository.fetchByName(fullName);
        if(userIdProjectionList.isEmpty())
            throw new Exception("user not found");
        long userId=userIdProjectionList.get(0).getId();
        EmailValidation validEmail=emailValidationRepository.fetchValidOtp(email,otp);
        if(validEmail==null)
            throw new Exception("OTP verification failed");
        userRepository.updateUserEmailStatus(userId);
    }

    @Override
    @Scheduled(cron = "0 */10 * * * *")
    public void deleteOtp() {
        OtpDeletionThread thread=new OtpDeletionThread(emailValidationRepository,lock2);
        exs2.submit(thread);
    }


    @PreDestroy
    public void threadShutDown() {
        exs1.shutdown();
        exs2.shutdown();
    }
}
