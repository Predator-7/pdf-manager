package com.pdfmanager.service;


import com.pdfmanager.dtos.ShareFileDto;
import com.pdfmanager.entity.SharedFiles;
import com.pdfmanager.repository.SharedFilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShareFileService {

    @Autowired
    private SharedFilesRepository sharedFilesRepository;

    public String saveFileShareDetails(ShareFileDto shareFileDto){

        try{
            SharedFiles sharedFiles = new SharedFiles();
            sharedFiles.setSenderId(shareFileDto.getSenderId());
            sharedFiles.setRecieverId(shareFileDto.getRecieverId());
            sharedFiles.setUrl(shareFileDto.getUrl());
            sharedFilesRepository.save(sharedFiles);

        }catch (RuntimeException ex){
            throw ex;
        }

        return "File Sent successfully!";


    }


}
