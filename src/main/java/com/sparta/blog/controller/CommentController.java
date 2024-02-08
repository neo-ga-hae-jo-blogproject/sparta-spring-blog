package com.sparta.blog.controller;

import com.sparta.blog.dto.CommentRequestDto;
import com.sparta.blog.dto.CommentResponseDto;
import com.sparta.blog.dto.CommonResponseDTO;
import com.sparta.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RejectedExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards/{boardId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public CommentResponseDto review(@PathVariable(name = "boardId") Long boardId, @RequestParam CommentRequestDto commentRequestDto) {
        CommentResponseDto commentResponseDto = commentService.review(commentRequestDto, boardId);
        return commentResponseDto;
    }

    @PutMapping("/update/{commentId}")
    public CommentResponseDto updateComment(@PathVariable(name = "commentId") Long commentId, @RequestParam CommentRequestDto commentRequestDto) {
        CommentResponseDto commentResponseDto = commentService.updateComment(commentRequestDto, commentId);
        return commentResponseDto;
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<CommonResponseDTO> deleteComment(@PathVariable(name = "commentId") Long commentId) {
        try {
            commentService.deleteComment(commentId);
            return ResponseEntity.ok().body(new CommonResponseDTO("정상적으로 삭제 되었습니다.", HttpStatus.OK.value()));
        } catch (RejectedExecutionException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new CommonResponseDTO(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}
