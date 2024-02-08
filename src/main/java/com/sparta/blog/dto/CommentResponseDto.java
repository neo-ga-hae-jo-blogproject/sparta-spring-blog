package com.sparta.blog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import com.sparta.blog.entity.Comment;



@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String content;
    private String username;
    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.username = comment.getUser().getUsername();
    }
}
