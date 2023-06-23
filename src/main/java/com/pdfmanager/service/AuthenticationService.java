package com.pdfmanager.service;

import com.pdfmanager.dtos.AuthUser;
import com.pdfmanager.dtos.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.net.UnknownServiceException;

@Service
@Log4j2
public class AuthenticationService {

    public User signup(AuthUser authUser){
        String authKey = authUser.getUserName() + authUser.getPassword();
        Integer authValue = authKey.length();

        log.info(authUser.getAuthKey());

        if(authValue == authUser.getAuthKey()){
            User user = new User(authUser.getUserName() , authUser.getEmail(), authUser.getPassword());

            // save to db
            return user;

        }
            throw new IllegalArgumentException("Invalid Credentials");
    }


}
