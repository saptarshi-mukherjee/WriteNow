package com.writenow.write.Services.EmailService;

public interface EmailService {
    public void sendEmail(String email);
    public void checkOtp(String fullName, String email, int otp) throws Exception;
    public void deleteOtp();
}
