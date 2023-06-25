package com.pdfmanager.repository;

import com.pdfmanager.entity.SharedFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
@Repository
@CrossOrigin(origins = "*", allowedHeaders = "*")
public interface SharedFilesRepository extends JpaRepository<SharedFiles , Long> {

    List<SharedFiles> findAllByRecieverId(Long receiverId);

}
