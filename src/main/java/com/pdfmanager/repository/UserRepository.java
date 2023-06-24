package com.pdfmanager.repository;

import com.pdfmanager.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "*", allowedHeaders = "*")
public interface UserRepository extends JpaRepository<Users, Long> {

        List<Users> findByUserName(String name);

        Optional<Users> findById(Long Id);

        List<Users> findByEmailAndUserName(String email, String userName);

        Users findByEmailAndUserNameAndPassword(String email, String userName, String password);

        Users findByEmailAndPassword(String email , String password);






}
