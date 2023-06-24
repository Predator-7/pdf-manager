package com.pdfmanager.common;

import org.springframework.stereotype.Component;

@Component
public class EncryptionUtils {

    public Integer encryptOtp(String userName , String password){
        Integer size = userName.length() + password.length();
        size *= 12121;
        return size;
    }
}
