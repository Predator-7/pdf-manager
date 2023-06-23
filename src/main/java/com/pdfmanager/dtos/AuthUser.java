package com.pdfmanager.dtos;

import lombok.Getter;

@Getter

public class AuthUser extends User{

    Integer authKey;

    public AuthUser() {
        super(); // Invoke parent class constructor
    }


}
