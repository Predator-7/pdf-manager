package com.pdfmanager.dtos;

import lombok.Getter;
import org.springframework.web.bind.annotation.CrossOrigin;

@Getter
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthUserDto extends UserDto {

    Integer authKey;

    public AuthUserDto() {
        super(); // Invoke parent class constructor
    }


}
