package com.pdfmanager.dtos;

import com.pdfmanager.entity.Comment;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class CommentDto {

    private String pdfId;

    private String content;

    private Comment parentComment;
    private List<Comment> childComments = new ArrayList<>();
}
