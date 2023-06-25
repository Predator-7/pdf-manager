package com.pdfmanager.service;

import com.pdfmanager.common.EncryptionUtils;
import com.pdfmanager.entity.Users;
import com.pdfmanager.dtos.AuthUserDto;
import com.pdfmanager.dtos.UserDto;
import com.pdfmanager.exception.InvalidParameterException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Objects;


// Conatains Login & Signup Controllers.

@Service
@Log4j2
@CrossOrigin
public class AuthenticationService {

    @Autowired
    private EncryptionUtils encryptionUtils;

    @Autowired
    private CrudService crudService;

    @CrossOrigin
    public Users signup(AuthUserDto authUser) {

        Integer authValue = encryptionUtils.encryptOtp(authUser.getUserName(), authUser.getPassword());

        if (!authValue.equals(authUser.getAuthKey())) {
            throw new InvalidParameterException("Invalid Credentials!");
        }

        UserDto userDto = new UserDto(authUser.getUserName(), authUser.getEmail(), authUser.getPassword());

        // Check for multiple entries
        List<Users> users = crudService.findUserByEmailAndUserName(userDto.getEmail(), userDto.getUserName());

        if (!CollectionUtils.isEmpty(users)) {

            log.info("User already registered!");
            throw new InvalidParameterException("User Already Registered!");
        }
        //  log.info(Objects.isNull(users) ? null : users.getId());// Making null safe
        Users users1 = crudService.saveUser(userDto);

        return users1;

    }

    public Users login(UserDto userDto) {
        Users users = crudService.findUserbyEmailAndPassword(userDto.getEmail(), userDto.getPassword());

        if (Objects.isNull(users)) {
            throw new InvalidParameterException("User Not found!");
        }

        Users users1 = new Users();
        users1.setUserName(users.getUserName());
        users1.setId(users.getId());
        users1.setEmail(userDto.getEmail());
        return users1;
    }


}
