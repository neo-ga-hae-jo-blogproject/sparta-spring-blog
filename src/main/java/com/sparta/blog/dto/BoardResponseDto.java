package com.sparta.blog.dto;

import com.sparta.blog.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BoardResponseDto {
    private Long id;
    private String username;
    private String title;
    private String content;
    private List<CommentResponseDto> commentList;

    public BoardResponseDto(Board board, List<CommentResponseDto> commentList) {
        this.id=board.getId();
        this.username = board.getUser().getUsername();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.commentList = commentList;
    }
}
