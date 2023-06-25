package com.pdfmanager.controller;

import com.pdfmanager.dtos.*;
import com.pdfmanager.entity.Files;
import com.pdfmanager.entity.SharedFiles;
import com.pdfmanager.entity.Users;
import com.pdfmanager.exception.InvalidParameterException;
import com.pdfmanager.service.CrudService;
import com.pdfmanager.service.FileStorageService;
import com.pdfmanager.service.ShareFileService;
import com.pdfmanager.service.UserService;
import lombok.extern.log4j.Log4j2;
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


@RestController
@Log4j2
@CrossOrigin(origins = "*", allowedHeaders = "*")
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

    @Autowired
    private FileStorageService fileStorageService;
    @CrossOrigin("*")
    @PostMapping("/upload")

    public ResponseEntity<MessageResponseDto> uploadFile(@RequestParam("file") MultipartFile file ,
                                                         @RequestParam("senderId") Long senderId,
                                                         @RequestParam("recieverId") List<Long> receiverIds) {
        String message = "";
       // log.info(uplaodFileDto.getSenderId());
        for(int i=0;i<receiverIds.size()-1;i++){
            log.info("Recieved ids - " + receiverIds.get(i));
        }

        try {
            storageService.store(file , senderId , receiverIds);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponseDto(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponseDto(message));
        }
    }

    @CrossOrigin("*")
    @GetMapping("/files")
    // Only for admin to see all the pdf files
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
    // Api to view the pdf file

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
//    @CrossOrigin("*")
//    @PostMapping("/share" )
//    public String shareFile(@RequestBody ShareFileDto shareFileDto){
//
//
//        Optional<Users> sender = crudService.getUserById(shareFileDto.getSenderId());
//
////        if(sender.isEmpty()){
////            throw new InvalidParameterException("Unauthorised sender!");
////        }
//
//        Optional<Users> reciever = crudService.getUserById(shareFileDto.getRecieverId());
//
////        if(reciever.isEmpty()){
////            throw new InvalidParameterException("Unauthorised reciever!");
////        }
//
//        return shareFileService.saveFileShareDetails(shareFileDto);
//    }

    @CrossOrigin(origins = "*") // Adjust the origin according to your frontend application's URL
    @PostMapping("/share")
    // Not used as we have implemented share function in the upload itself
    public ResponseEntity<String> shareFile(@RequestBody ShareFileDto shareFileDto) {
        Optional<Users> sender = crudService.getUserById(shareFileDto.getSenderId());
//
//        if (sender.isEmpty()) {
//            throw new InvalidParameterException("Unauthorized sender!");
//        }

     //   Optional<Users> receiver = crudService.getUserById(shareFileDto.getRecieverId());

//        if (receiver.isEmpty()) {
//            throw new InvalidParameterException("Unauthorized receiver!");
//        }

        shareFileService.saveFileShareDetails(shareFileDto);

        return ResponseEntity.ok("File share details saved successfully.");
    }

    // Get Inbox
    @CrossOrigin("*")
    @GetMapping("inbox/sharedPdfs")
    // Used to view the pdfs shared to user
    public List<InboxResponseDto> getSharedPdfs(@RequestParam Long id){
        Optional<Users> users = crudService.getUserById(id);

        if(users.isEmpty()){
            throw new InvalidParameterException("Unauthorised User!");
        }

        List<SharedFiles> sharedFiles = shareFileService.getSharedPdfs(id);

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

    @CrossOrigin("*")
    @GetMapping("inbox/uploadedPdfs")
    // Api used to show the pdfs uploaded by user
    public List<InboxResponseDto> getUploadedPdfs(@RequestParam Long id){
        Optional<Users> users = crudService.getUserById(id);

        if(users.isEmpty()){
            throw new InvalidParameterException("Unauthorised User!");
        }

        List<SharedFiles> sharedFiles = shareFileService.getUploadedPdfs(id);

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

    // Api to send the existing pdf in dashboard

    @PostMapping("shareExistingFile")
    public String shareExistingFile(@RequestBody  ShareExistingFileDto shareExistingFileDto){

        if(Objects.isNull(shareExistingFileDto)){
            throw new InvalidParameterException("Invalid parameters!");
        }

        return fileStorageService.saveDataInSharedFilesDb(shareExistingFileDto.getUrl() , shareExistingFileDto.getSenderId(), shareExistingFileDto.getRecieverId() , shareExistingFileDto.getFilename());

    }











}
