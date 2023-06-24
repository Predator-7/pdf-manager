package com.pdfmanager.controller;

import com.pdfmanager.dtos.AuthUserDto;
import com.pdfmanager.dtos.UserDto;

import com.pdfmanager.entity.Users;
import com.pdfmanager.service.AuthenticationService;
import com.pdfmanager.service.EmailService;
import com.pdfmanager.common.EncryptionUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("pdf-manager")
public class Controllers {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EncryptionUtils encryptionUtils;
    @CrossOrigin
    @GetMapping("test")
    public String test(){
        return "test";
    }

    @CrossOrigin
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
    @CrossOrigin("*")
    @PostMapping("signup")
    public Users signup(@RequestBody AuthUserDto authUser) {

        return authenticationService.signup(authUser);
    }


    @CrossOrigin("*")
    @PostMapping(value = "login" , produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Users login(@RequestBody UserDto userDto){

        log.info(userDto.getEmail());
        log.info(userDto.getUserName());
        log.info(userDto.getPassword());

//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Access-Control-Allow-Origin", "*"); // Allow requests from all origins
//        headers.add("Access-Control-Allow-Methods", "POST"); // Allow POST requests
//        headers.add("Access-Control-Allow-Headers", "Content-Type"); // Allow the Content-Type header

        return authenticationService.login(userDto);
    }

//    @CrossOrigin("*")
//    public Users getUserProfile(UserDto userDto){
//
//
//    }

}
