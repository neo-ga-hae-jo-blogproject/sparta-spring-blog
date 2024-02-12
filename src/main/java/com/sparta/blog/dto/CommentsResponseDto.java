package com.sparta.blog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import com.sparta.blog.entity.Comment;



@Getter
@NoArgsConstructor
public class CommentsResponseDto {
    private String content;
    private String username;
    public CommentsResponseDto(Comment comment) {
        this.content = comment.getContent();
        this.username = comment.getUser().getUsername();
    }
}