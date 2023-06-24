package com.pdfmanager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.bind.annotation.GetMapping;

@Entity
@Getter
@Setter
@Table(name = "files")
public class Files {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private String type;

    @Lob
    @Column(name = "data")
    private byte[] data;

}
