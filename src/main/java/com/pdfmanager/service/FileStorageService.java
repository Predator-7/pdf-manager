package com.pdfmanager.service;

import com.pdfmanager.entity.Files;
import com.pdfmanager.repository.FileRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
@Log4j2
public class FileStorageService {

    @Autowired
    private FileRepository fileRepository;

    public Files store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());


        byte[] fileBytes = file.getBytes();

        Files files = new Files();
        files.setName(fileName);
        files.setType(file.getContentType());
        files.setData(fileBytes);
        log.info(files.getData());

        return fileRepository.save(files);
    }


    public Files getFile(String id) {
        return fileRepository.findById(id).get();
    }

    public Stream<Files> getAllFiles() {
        return fileRepository.findAll().stream();
    }



}
