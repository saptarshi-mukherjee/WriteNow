package com.writenow.write.Helpers;


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


}
