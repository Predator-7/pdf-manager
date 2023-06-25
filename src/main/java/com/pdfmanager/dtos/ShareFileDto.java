package com.pdfmanager.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.CrossOrigin;

@Getter
@Setter
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ShareFileDto {

    private Long senderId;

    private Long recieverId;

    private String url;

    private String filename;
}
