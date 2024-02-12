package com.sparta.blog.dto;

import com.sparta.blog.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BoardsResponseDto {
    private Long id;
    private String username;
    private String title;
    private String content;
    private List<CommentsResponseDto> commentList;

    public BoardsResponseDto(Board board, List<CommentsResponseDto> commentList) {
        this.id=board.getBoardId();
        this.username = board.getUser().getUsername();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.commentList = commentList;
    }
}