package com.pdfmanager.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class InboxResponseDto {

    private Long senderId;

    private String senderName;

    private String pdfUrl;

    private String pdfName;
}
