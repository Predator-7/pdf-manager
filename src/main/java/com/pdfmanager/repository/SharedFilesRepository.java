package com.pdfmanager.repository;

import com.pdfmanager.entity.SharedFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SharedFilesRepository extends JpaRepository<SharedFiles , Long> {

    List<SharedFiles> findAllByRecieverId(Long receiverId);

}
