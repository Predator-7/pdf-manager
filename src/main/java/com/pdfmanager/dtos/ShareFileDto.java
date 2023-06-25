package com.pdfmanager.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShareFileDto {

    private Long senderId;

    private Long recieverId;

    private String url;

    private String filename;
}
