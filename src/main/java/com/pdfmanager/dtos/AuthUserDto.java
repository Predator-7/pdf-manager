package com.pdfmanager.dtos;

import lombok.Getter;

@Getter

public class AuthUserDto extends UserDto {

    Integer authKey;

    public AuthUserDto() {
        super(); // Invoke parent class constructor
    }


}
