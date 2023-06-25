package com.pdfmanager.service;

import com.pdfmanager.dtos.UserDto;
import com.pdfmanager.entity.Users;
import com.pdfmanager.exception.InternalServerException;
import com.pdfmanager.exception.InvalidParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private CrudService crudService;

    public Users getUserProfile(UserDto userDto){

        Users users1 = crudService.findUserbyEmailAndPassword(userDto.getEmail() , userDto.getPassword());

        if(Objects.isNull(users1)){
            throw new InvalidParameterException("User Not Found!");
        }

        return users1;

    }

    public List<Users> getAllUsers(){
        List<Users> users = crudService.getAllUsers();


        return users;
    }

    public String getUserName(Long id){
        Optional<Users> users = crudService.getUserById(id);

        if(users.isEmpty()){
            return null;
        }

        return users.get().getUserName();

    }

}
