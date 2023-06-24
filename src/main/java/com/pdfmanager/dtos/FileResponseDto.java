package com.pdfmanager.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FileResponseDto {

    private String name;
    private String url;
    private String type;
    private Long size;

}
