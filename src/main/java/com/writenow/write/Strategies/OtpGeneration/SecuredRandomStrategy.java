package com.writenow.write.Strategies.OtpGeneration;

import com.writenow.write.Models.EmailValidation;
import com.writenow.write.Repositories.EmailValidationRepository;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.List;

public class SecuredRandomStrategy implements OtpGenerationStrategy {

    private EmailValidationRepository emailValidationRepository;

    public SecuredRandomStrategy(EmailValidationRepository emailValidationRepository) {
        this.emailValidationRepository = emailValidationRepository;
    }

    @Override
    public int generateOtp(String email) {
        int otp=generate();
        EmailValidation emailValidation=new EmailValidation();
        emailValidation.setEmail(email);
        emailValidation.setOtp(otp);
        emailValidation=emailValidationRepository.save(emailValidation);
        return emailValidation.getOtp();
    }

    private int generate() {
        HashSet<Integer> set=new HashSet<>();
        List<EmailValidation> emailValidationList=emailValidationRepository.fetchAllOtp();
        for(EmailValidation e : emailValidationList)
            set.add(e.getOtp());
        int otp=-1;
        SecureRandom secureRandom=new SecureRandom();
        do {
            otp= secureRandom.nextInt(899999)+100000;
        } while (set.contains(otp));
        return otp;
    }
}
