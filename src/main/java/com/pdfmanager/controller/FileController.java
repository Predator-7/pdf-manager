package com.pdfmanager.controller;

import com.pdfmanager.dtos.FileResponseDto;
import com.pdfmanager.dtos.InboxResponseDto;
import com.pdfmanager.dtos.MessageResponseDto;
import com.pdfmanager.dtos.ShareFileDto;
import com.pdfmanager.entity.Files;
import com.pdfmanager.entity.SharedFiles;
import com.pdfmanager.entity.Users;
import com.pdfmanager.exception.InvalidParameterException;
import com.pdfmanager.service.CrudService;
import com.pdfmanager.service.FileStorageService;
import com.pdfmanager.service.ShareFileService;
import com.pdfmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("pdf-manager")
public class FileController {

    @Autowired
    private FileStorageService storageService;

    @Autowired
    private ShareFileService shareFileService;

    @Autowired
    private CrudService crudService;

    @Autowired
    private UserService userService;

    @PostMapping("/upload")
    @CrossOrigin("*")
    public ResponseEntity<MessageResponseDto> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        try {
            storageService.store(file);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponseDto(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponseDto(message));
        }
    }


    @GetMapping("/files")
    @CrossOrigin("*")
    public ResponseEntity<List<FileResponseDto>> getListFiles() {
        List<FileResponseDto> files = storageService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("pdf-manager/files/")
                    .path(dbFile.getId().toString())
                    .toUriString();

            return new FileResponseDto(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    (long)dbFile.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @CrossOrigin("*")
    @GetMapping("/files/{id}")

    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        Files files = storageService.getFile(id);

        if(Objects.isNull(files)){
            throw new InvalidParameterException("Pdf Not Found!");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.set("Content-Disposition", "inline; filename=\"" + files.getName() + "\"");
        headers.setContentLength(files.getData().length);

        return new ResponseEntity<>(files.getData(), headers, HttpStatus.OK);
    }


    // Share Api to share the pdfs
    @CrossOrigin("*")
    @PostMapping("/share")

    private String shareFile(@RequestBody ShareFileDto shareFileDto){

        Optional<Users> sender = crudService.getUserById(shareFileDto.getSenderId());

        if(sender.isEmpty()){
            throw new InvalidParameterException("Unauthorised sender!");
        }

        Optional<Users> reciever = crudService.getUserById(shareFileDto.getRecieverId());

        if(reciever.isEmpty()){
            throw new InvalidParameterException("Unauthorised reciever!");
        }

        return shareFileService.saveFileShareDetails(shareFileDto);
    }

    // Get Inbox

    @GetMapping("inbox")
    @CrossOrigin("*")
    private List<InboxResponseDto> getInbox(@RequestParam Long id){
        Optional<Users> users = crudService.getUserById(id);

        if(users.isEmpty()){
            throw new InvalidParameterException("Unauthorised User!");
        }

        List<SharedFiles> sharedFiles = shareFileService.getInbox(id);

        List<InboxResponseDto> dtoList = sharedFiles.stream()
                .map(sharedFile -> {
                    InboxResponseDto dto = new InboxResponseDto();
                    dto.setSenderId(sharedFile.getSenderId());
                    dto.setPdfUrl(sharedFile.getUrl());
                    dto.setPdfName(sharedFile.getFilename());
                    dto.setSenderName(userService.getUserName(sharedFile.getSenderId()));
                    return dto;
                })
                .collect(Collectors.toList());

        return dtoList;
    }







}
