package com.pdfmanager.dtos;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;
@Getter
@Service
public class ShareExistingFileDto {

    private Long senderId;

    private List<Long> recieverId;

    private String url;

    private String filename;
}
