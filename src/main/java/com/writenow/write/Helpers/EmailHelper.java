package com.writenow.write.Helpers;


import com.writenow.write.Models.ComplianceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

public class EmailHelper {

    public static void sendEmail(JavaMailSender mailSender, String email, int otp) {
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(email);
        message.setFrom("sapbum1234@gmail.com");
        message.setSubject("OTP for Email validation");
        message.setText("OTP is "+otp+". Valid for 15 minutes");
        mailSender.send(message);
    }


    public static void sendComplianceMail(JavaMailSender mailSender, String email, ComplianceStatus status) {
        String msg=null;
        if(status==ComplianceStatus.NOT_COMPLIANT)
            msg="Submitted story is not compliant to one or more rules of the contest and hence disqualified";
        else
            msg="Submitted story is compliant and forwarded for judgement";
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setFrom("sapbum1234@gmail.com");
        mailMessage.setSubject("Compliance check");
        mailMessage.setText(msg);
        mailSender.send(mailMessage);

    }


}
