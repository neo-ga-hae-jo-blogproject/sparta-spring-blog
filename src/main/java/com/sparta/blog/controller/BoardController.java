package com.sparta.blog.controller;

import com.sparta.blog.dto.BoardResponseDto;
import com.sparta.blog.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/list")
    public List<BoardResponseDto> getBoardList(){
        return boardService.getBoardList();
    }

    @GetMapping("/list/{id}")
    public BoardResponseDto getBoardDetail(@PathVariable Long id) {
     return boardService.getBoardDetail(id);
    }
}
