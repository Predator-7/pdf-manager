package com.pdfmanager.service;

import com.pdfmanager.dtos.ShareFileDto;
import com.pdfmanager.entity.Files;
import com.pdfmanager.repository.FileRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@Service
@Log4j2
@CrossOrigin("*")
public class FileStorageService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private ShareFileService shareFileService;

    public Files store(MultipartFile file , Long senderId , List<Long> recieverId) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());


        byte[] fileBytes = file.getBytes();

        Files files = new Files();
        files.setName(fileName);
        files.setType(file.getContentType());
        files.setData(fileBytes);

        // Saved the uploaded file from the sender in the db.
        // Now will update the shared_file to add access to users who can access it.

        Files savedFile = fileRepository.save(files);

        String id = savedFile.getId();

        // passing url as id , senderId , list of recieverId to save the details in shared_files;
        log.info(id);

        String saveDetails = saveDataInSharedFilesDb(id , senderId , recieverId , fileName);

        // Got the uploaded file url.


        // Here the table is updated which gives that who can access which pdfs

        return savedFile;
    }


    public String saveDataInSharedFilesDb(String id , Long senderId , List<Long> recieverId , String fileName){

        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("pdf-manager/files/")
                .path(id.toString())
                .toUriString();
        log.info(fileDownloadUri);

        log.info(recieverId.size());

        for(int i=0;i<recieverId.size()-1;i++){

            ShareFileDto shareFileDto = new ShareFileDto();
            shareFileDto.setFilename(fileName);
            shareFileDto.setUrl(fileDownloadUri);
            shareFileDto.setRecieverId(recieverId.get(i));
            shareFileDto.setSenderId(senderId);

            String temp = shareFileService.saveFileShareDetails(shareFileDto);

            log.info(temp + "to " + recieverId.get(i));
        }

        return "Done";


    }


    public Files getFile(String id) {
        return fileRepository.findById(id).get();
    }

    public Stream<Files> getAllFiles() {
        return fileRepository.findAll().stream();
    }



}
