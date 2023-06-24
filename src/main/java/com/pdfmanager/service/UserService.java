package com.pdfmanager.service;

import com.pdfmanager.dtos.UserDto;
import com.pdfmanager.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private CrudService crudService;

    public Users getUserProfile(UserDto userDto){

        Users users = crudService.findUserbyEmailAndPassword(userDto.getEmail() , userDto.getPassword());

        if(Objects.isNull(users)){
            throw  new IllegalArgumentException("User Not Found!");
        }

        return users;

    }

}
