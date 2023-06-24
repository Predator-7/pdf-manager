package com.pdfmanager.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorResponseDto {
    private int status;
    private String message;
    private long timestamp;
}
