package com.pdfmanager.repository;

import com.pdfmanager.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

        List<Users> findByUserName(String name);

        Optional<Users> findById(Long Id);

        Users findByEmailAndUserName(String email, String userName);






}
