package com.sparta.blog.dto;

import lombok.Getter;

@Getter
public class CommentResponseDto {
    private String content;
    private String writer;

    public CommentResponseDto(String content,String writer) {
        this.content = content;
        this.writer =writer;
    }
}
