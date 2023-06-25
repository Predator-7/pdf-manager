package com.pdfmanager.controller;

import com.pdfmanager.entity.Comment;
import com.pdfmanager.repository.CommentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("pdf-manager")
public class CommentController {

    private final CommentRepository commentRepository;

    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @GetMapping("comments")
    @CrossOrigin("*")
    public List<Comment> getComments() {
        return commentRepository.findByParentCommentIsNull();
    }

    @PostMapping("createComment")
    @CrossOrigin("*")
    public Comment createComment(@RequestBody Comment comment) {
        return commentRepository.save(comment);
    }
}
