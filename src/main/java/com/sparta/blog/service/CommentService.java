package com.sparta.blog.service;

import com.sparta.blog.dto.CommentRequestDto;
import com.sparta.blog.dto.CommentResponseDto;
import com.sparta.blog.entity.Comment;
import com.sparta.blog.entity.User;
import com.sparta.blog.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentResponseDto review(CommentRequestDto commentRequestDto,Long boardId){
        Comment comment = new Comment(commentRequestDto.getContent(), boardId);
        commentRepository.save(comment);
        return new CommentResponseDto(comment.getContent());
    }

    public CommentResponseDto updateComment(CommentRequestDto commentRequestDto,Long commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        comment.updateContent(commentRequestDto.getContent());
        return new CommentResponseDto(comment.getContent());
    }

    public void deleteComment(Long commentId){
        commentRepository.deleteById(commentId);
    }



}
