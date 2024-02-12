package com.sparta.blog.dto;

import com.sparta.blog.entity.Board;
import lombok.Getter;

@Getter
public class BoardResponseDto {
    private String title;
    private String content;

    public BoardResponseDto(Board board){
        this.title = board.getTitle();
        this.content = board.getContent();
    }

}
