package com.pdfmanager.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class UploadFileDto {

    private Long senderId;

    private List<Long> recieverId;
}
