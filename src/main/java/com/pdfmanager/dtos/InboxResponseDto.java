package com.pdfmanager.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

@Getter
@Setter
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class InboxResponseDto {

    private Long senderId;

    private String senderName;

    private String pdfUrl;

    private String pdfName;
}
