package com.pdfmanager.service;

import com.pdfmanager.dtos.UserDto;
import com.pdfmanager.entity.Users;
import com.pdfmanager.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@CrossOrigin("*")
public class CrudService {

    @Autowired
    private UserRepository userRepository;
    @CrossOrigin("*")
    public List<Users> getCustomersByName(String name) {
        return userRepository.findByUserName(name);
    }
    @CrossOrigin("*")
    public Optional<Users> getUserById(Long id) {
        return userRepository.findById(id);
    }
    @CrossOrigin("*")
    public List<Users> getCustomers(){
        return userRepository.findAll();
    }
    @CrossOrigin("*")
    public List<Users> findUserByEmailAndUserName(String email, String userName) {
        return userRepository.findByEmailAndUserName(email, userName);
    }
    // findByEmailAndUserNameAndPassword(String email, String userName, String password);


    @CrossOrigin("*")
    public Users verifyUser(String email, String userName, String password){
        return userRepository.findByEmailAndUserNameAndPassword(email,userName,password);
    }
    @CrossOrigin("*")
    public Users findUserbyEmailAndPassword(String email , String password){
        return userRepository.findByEmailAndPassword(email , password);
    }
    @CrossOrigin("*")
    public Users findByIdAndEmail(Long id , String email){
        return userRepository.findByIdAndEmail(id , email);
    }



    @CrossOrigin("*")
    public Users saveUser(UserDto userDto){

        Users users = new Users();
        users.setUserName(userDto.getUserName());
        users.setEmail( userDto.getEmail());
        users.setPassword(userDto.getPassword());
        userRepository.save(users);
        return users;
    }
    @CrossOrigin("*")
    public List<Users> getAllUsers(){
        return userRepository.findAll();
    }
}
