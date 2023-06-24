package com.pdfmanager.controller;

import com.pdfmanager.dtos.AuthUserDto;
import com.pdfmanager.dtos.UserDto;

import com.pdfmanager.entity.Users;
import com.pdfmanager.exception.InternalServerException;
import com.pdfmanager.service.AuthenticationService;
import com.pdfmanager.service.EmailService;
import com.pdfmanager.common.EncryptionUtils;
import com.pdfmanager.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    private UserService userService;


    @CrossOrigin
    @GetMapping("test")
    public String test(){
        return "test";
    }

    @CrossOrigin
    @PostMapping(value = "getOtp" , produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String getOtp(@RequestBody UserDto userDto){

        try {

            Integer otp = encryptionUtils.encryptOtp(userDto.getUserName(), userDto.getPassword());

            emailService.sendEmail(userDto.getEmail(), "Please verify your email", "Your otp is - " + otp);

        } catch (MailException ex){
            log.info(ex.getMessage());
            throw new InternalServerException(ex.getMessage());

        }

        return "Email sent";

    }
    @CrossOrigin("*")
    @PostMapping(value = "signup" , produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Users signup(@RequestBody AuthUserDto authUser) {

        return authenticationService.signup(authUser);
    }


    @CrossOrigin("*")
    @PostMapping(value = "login" , produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Users login(@RequestBody UserDto userDto){

        return authenticationService.login(userDto);
    }

    @CrossOrigin("*")
    @GetMapping(value = "profile" , produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Users getUserProfile(@RequestBody UserDto userDto){

        return userService.getUserProfile(userDto);
    }

    @CrossOrigin("*")
    @GetMapping(value = "users" , produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Users> getAllUsers(){
        return userService.getAllUsers();
    }

}
