package com.sparta.blog.dto;

import com.sparta.blog.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private String content;
    private String writer;

    public CommentResponseDto(Comment comment) {
        this.content = comment.getContent();
        this.writer = comment.getUser().getUsername();
    }
}
