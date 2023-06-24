package com.pdfmanager.service;

import com.pdfmanager.common.EncryptionUtils;
import com.pdfmanager.entity.Users;
import com.pdfmanager.dtos.AuthUserDto;
import com.pdfmanager.dtos.UserDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Log4j2
public class AuthenticationService {

    @Autowired
    private EncryptionUtils encryptionUtils;

    @Autowired
    private CrudService crudService;

    public Users signup(AuthUserDto authUser){

        Integer authValue = encryptionUtils.encryptOtp(authUser.getUserName(), authUser.getPassword());


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

            if(Objects.isNull(users)){
                // We have to throw error user already registered!
            }
            log.info(Objects.isNull(users) ? null : users.getId());// Making null safe
            Users users1 = crudService.saveUser(userDto);

            return users1;

        }
            throw new IllegalArgumentException("Invalid Credentials");
    }


}
