package com.writenow.write.Controllers.EmailValidationController;


import com.writenow.write.DTO.RequestDto.EmailValidationRequestDto;
import com.writenow.write.Services.EmailService.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/validate")
public class EmailValidationController {

    @Autowired
    private EmailService emailService;


    @PostMapping("/send/{email}")
    public void getOtp(@PathVariable("email") String email) {
        emailService.sendEmail(email);
    }


    @GetMapping("/check")
    public void checkOtp(@RequestParam("name") String name,
                         @RequestParam("email") String email,
                         @RequestParam("otp") int otp) throws Exception {
        emailService.checkOtp(name, email, otp);
    }
}
