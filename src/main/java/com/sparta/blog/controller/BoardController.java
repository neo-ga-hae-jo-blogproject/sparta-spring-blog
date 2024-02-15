package com.sparta.blog.controller;

import com.sparta.blog.dto.BoardRequestDto;
import com.sparta.blog.dto.BoardResponseDto;
import com.sparta.blog.dto.BoardListResponseDto;
import com.sparta.blog.security.UserDetailsImpl;
import com.sparta.blog.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PostMapping
    public BoardResponseDto createBoard(@AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @RequestBody BoardRequestDto requestDto) {
        return boardService.createBoard(userDetails, requestDto);
    }

    @PutMapping("/{id}")
    public BoardResponseDto updateBoard(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @Valid @RequestBody BoardRequestDto requestDto) {
        return boardService.updateBoard(userDetails, id, requestDto);
    }

    @DeleteMapping("/{id}")
    public String deleteBoard(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        return boardService.deleteBoard(userDetails, id);
    }
}
