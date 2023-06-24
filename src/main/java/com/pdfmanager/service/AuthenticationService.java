package com.pdfmanager.service;

import com.pdfmanager.Entity.Users;
import com.pdfmanager.dtos.AuthUserDto;
import com.pdfmanager.dtos.UserDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Log4j2
public class AuthenticationService {

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private CrudService crudService;

    public UserDto signup(AuthUserDto authUser){

        Integer authValue = encryptionService.encryptOtp(authUser.getUserName(), authUser.getPassword());


        log.info(authUser.getAuthKey());
        log.info(authValue);

        if(authValue.equals(authUser.getAuthKey())){
            UserDto userDto = new UserDto(authUser.getUserName() , authUser.getEmail(), authUser.getPassword());
            // save to db

        //    List<Users> users = crudService.getCustomersByName("ansh");
       //     log.info(users.size());

       //     Optional<Users> users1 = crudService.getCustomersById(1l);
       //     log.info(users1.get().getUserName());

            Users users = crudService.findUserByEmailAndUserName(userDto.getEmail(), userDto.getUserName());
            log.info(Objects.isNull(users) ? null : users.getId());  // Making null safe



            return userDto;

        }
            throw new IllegalArgumentException("Invalid Credentials");
    }


}
