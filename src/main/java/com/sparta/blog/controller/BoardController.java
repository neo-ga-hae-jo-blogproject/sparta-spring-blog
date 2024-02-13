package com.sparta.blog.controller;

import com.sparta.blog.dto.BoardRequestDto;
import com.sparta.blog.dto.BoardResponseDto;
import com.sparta.blog.dto.BoardListResponseDto;
import com.sparta.blog.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {
    private final BoardService boardService;

    @GetMapping
    public List<BoardListResponseDto> getBoardList(){
        return boardService.getBoardList();
    }

    @GetMapping("/{id}")
    public BoardListResponseDto getBoardDetail(@PathVariable Long id) {
     return boardService.getBoardDetail(id);
    }

    @PostMapping("/create")
    public BoardResponseDto createBoard(@RequestHeader(value = "Authorization") String token, @Valid @RequestBody BoardRequestDto requestDto) {
        return boardService.createBoard(token, requestDto);
    }

    @PutMapping("/{id}/update")
    public BoardResponseDto updateBoard(@RequestHeader(value = "Authorization") String token, @PathVariable Long id, @Valid @RequestBody BoardRequestDto requestDto) {
        return boardService.updateBoard(token, id, requestDto);
    }

    @DeleteMapping("/{id}/delete")
    public String deleteBoard(@RequestHeader(value = "Authorization") String token, @PathVariable Long id) {
        return boardService.deleteBoard(token, id);
    }
}
