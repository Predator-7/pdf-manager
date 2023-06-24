package com.pdfmanager.service;

import com.pdfmanager.common.EncryptionUtils;
import com.pdfmanager.entity.Users;
import com.pdfmanager.dtos.AuthUserDto;
import com.pdfmanager.dtos.UserDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Objects;

@Service
@Log4j2
@CrossOrigin
public class AuthenticationService {

    @Autowired
    private EncryptionUtils encryptionUtils;

    @Autowired
    private CrudService crudService;
    @CrossOrigin
    public Users signup(AuthUserDto authUser){

        Integer authValue = encryptionUtils.encryptOtp(authUser.getUserName(), authUser.getPassword());


        log.info(authUser.getAuthKey());
        log.info(authValue);

        if(authValue.equals(authUser.getAuthKey())){
            UserDto userDto = new UserDto(authUser.getUserName() , authUser.getEmail(), authUser.getPassword());
            // save to db

            List<Users> users = crudService.findUserByEmailAndUserName(userDto.getEmail(), userDto.getUserName());

            if(!CollectionUtils.isEmpty(users)){
                // We have to throw error user already registered!
                log.info("User already registered!");
            }
          //  log.info(Objects.isNull(users) ? null : users.getId());// Making null safe
            Users users1 = crudService.saveUser(userDto);

            return users1;

        }
            throw new IllegalArgumentException("Invalid Credentials");
    }

    public Users login(UserDto userDto){
        Users users = crudService.verifyUser(userDto.getEmail(), userDto.getUserName(), userDto.getPassword());

        if(!Objects.isNull(users)){
             Users users1= new Users();
             users1.setUserName(userDto.getUserName());
             users1.setEmail(userDto.getEmail());
             return users1;
        }

        else{
            throw new IllegalArgumentException("User Not found!");
        }

    }


}
