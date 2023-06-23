package com.pdfmanager.service;

import com.pdfmanager.dtos.AuthUser;
import com.pdfmanager.dtos.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.UnknownServiceException;

@Service
@Log4j2
public class AuthenticationService {

    @Autowired
    private EncryptionService encryptionService;

    public User signup(AuthUser authUser){

        Integer authValue = encryptionService.encryptOtp(authUser.getUserName(), authUser.getPassword());


        log.info(authUser.getAuthKey());
        log.info(authValue);

        if(authValue.equals(authUser.getAuthKey())){
            User user = new User(authUser.getUserName() , authUser.getEmail(), authUser.getPassword());
            // save to db
            return user;

        }
            throw new IllegalArgumentException("Invalid Credentials");
    }


}
