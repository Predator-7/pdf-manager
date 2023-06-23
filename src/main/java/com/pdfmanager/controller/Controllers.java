package com.pdfmanager.controller;

import com.pdfmanager.dtos.AuthUser;
import com.pdfmanager.dtos.User;

import com.pdfmanager.service.AuthenticationService;
import com.pdfmanager.service.EmailService;
import com.pdfmanager.service.EncryptionService;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.PerformanceSensitive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("pdf-manager")
public class Controllers {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EncryptionService encryptionService;

    @GetMapping("test")
    public String test(){
        return "test";
    }


    @PostMapping("getOtp")
    public String getOtp(@RequestBody User user){

       // String otp = (user.getUserName() + user.getPassword()).toString();

        try {

            Integer otp = encryptionService.encryptOtp(user.getUserName(), user.getPassword());

            emailService.sendEmail(user.getEmail(), "Please verify your email", "Your otp is - " + otp);

        } catch (MailException ex){
            log.info(ex.getMessage());
        }

        return "Email sent";


    }


    @PostMapping("signup")
    public User signup(@RequestBody AuthUser authUser) {

        return authenticationService.signup(authUser);
    }


}
