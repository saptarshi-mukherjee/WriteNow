package com.writenow.write.Threads;

import com.writenow.write.Helpers.EmailHelper;
import com.writenow.write.Models.ComplianceStatus;
import com.writenow.write.Services.ComplianceCheckService.ComplianceCheckService;
import org.springframework.mail.javamail.JavaMailSender;

public class ComplianceCheckThread implements Runnable {

    private ComplianceCheckService complianceCheckService;
    private Object lock;
    private Long storyId;
    private JavaMailSender mailSender;
    private String email;

    public ComplianceCheckThread(ComplianceCheckService complianceCheckService, Object lock, Long storyId,
                                 JavaMailSender mailSender, String email) {
        this.complianceCheckService = complianceCheckService;
        this.lock = lock;
        this.storyId=storyId;
        this.mailSender=mailSender;
        this.email=email;
    }

    @Override
    public void run() {
        synchronized (lock) {
            ComplianceStatus status=complianceCheckService.check(storyId);
            EmailHelper.sendComplianceMail(mailSender,email,status);
        }
    }
}
