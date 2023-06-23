package com.pdfmanager.controller;

import com.pdfmanager.dtos.AuthUser;
import com.pdfmanager.dtos.User;

import com.pdfmanager.service.AuthenticationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("pdf-manager")
public class Controllers {

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("test")
    public String test(){
        return "test";
    }


    @PostMapping("signup")
    public User signup(@RequestBody AuthUser authUser) {

      //  log.info(authUser.getAuthKey());


        return authenticationService.signup(authUser);

    }
}
