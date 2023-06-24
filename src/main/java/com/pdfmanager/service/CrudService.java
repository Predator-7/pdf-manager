package com.pdfmanager.service;

import com.pdfmanager.Entity.Users;
import com.pdfmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CrudService {

    @Autowired
    private UserRepository userRepository;

    public List<Users> getCustomersByName(String name) {
        return userRepository.findByUserName(name);
    }

    public Optional<Users> getCustomersById(Long id) {
        return userRepository.findById(id);
    }

    public List<Users> getCustomers(){
        return userRepository.findAll();
    }

    public Users findUserByEmailAndUserName(String email, String userName) {
        return userRepository.findByEmailAndUserName(email, userName);
    }
}
