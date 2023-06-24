package com.pdfmanager.controller;

import com.pdfmanager.dtos.AuthUserDto;
import com.pdfmanager.dtos.UserDto;

import com.pdfmanager.entity.Users;
import com.pdfmanager.service.AuthenticationService;
import com.pdfmanager.service.EmailService;
import com.pdfmanager.common.EncryptionUtils;
import lombok.extern.log4j.Log4j2;
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
    private EncryptionUtils encryptionUtils;

    @GetMapping("test")
    public String test(){
        return "test";
    }


    @PostMapping("getOtp")
    public String getOtp(@RequestBody UserDto userDto){

       // String otp = (user.getUserName() + user.getPassword()).toString();

        try {

            Integer otp = encryptionUtils.encryptOtp(userDto.getUserName(), userDto.getPassword());

            emailService.sendEmail(userDto.getEmail(), "Please verify your email", "Your otp is - " + otp);

        } catch (MailException ex){
            log.info(ex.getMessage());
        }

        return "Email sent";

    }


    @PostMapping("signup")
    public Users signup(@RequestBody AuthUserDto authUser) {

        return authenticationService.signup(authUser);
    }


}
