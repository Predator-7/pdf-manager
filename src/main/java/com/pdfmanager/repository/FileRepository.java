package com.pdfmanager.repository;

import com.pdfmanager.entity.Files;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Repository
public interface FileRepository extends JpaRepository<Files , String> {


}
