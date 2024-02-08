package com.sparta.blog.controller;

import com.sparta.blog.dto.BoardRequestDto;
import com.sparta.blog.dto.BoardResponseDto;
import com.sparta.blog.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {
    private final BoardService boardService;
    @PostMapping("/create")
    public BoardResponseDto createBoard(@RequestHeader(value = "Authorization") String token, @Valid @RequestBody BoardRequestDto requestDto) {
        return boardService.createBoard(token, requestDto);
    }

//    @PutMapping("/update/{boardId}")
//    public BoardResponseDto updateBoard(@RequestHeader(value = "Authorization") String token, @PathVariable Long boardId, @Valid @RequestBody BoardRequestDto requestDto) {
//        return boardService.updateBoard(token, boardId, requestDto);
//    }
//
//    @DeleteMapping("/delete/{boardId}")
//    public String deleteBoard(@RequestHeader(value = "Authorization") String token, @PathVariable Long boardId) {
//        return boardService.deleteBoard(token, boardId);
//    }
}


