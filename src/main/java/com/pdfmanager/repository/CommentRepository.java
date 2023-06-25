package com.pdfmanager.repository;

import com.pdfmanager.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByParentCommentIsNull();
}
