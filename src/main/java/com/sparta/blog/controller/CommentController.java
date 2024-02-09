package com.sparta.blog.controller;

import com.sparta.blog.dto.CommentRequestDto;
import com.sparta.blog.dto.CommentResponseDto;
import com.sparta.blog.dto.CommonResponseDto;
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
    public CommentResponseDto review(@RequestHeader(value = "Authorization") String token, @PathVariable(name = "boardId") Long boardId, @RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto commentResponseDto = commentService.review(commentRequestDto, boardId, token);
        return commentResponseDto;
    }

    @PutMapping("/update/{commentId}")
    public CommentResponseDto updateComment(@RequestHeader(value = "Authorization") String token, @PathVariable(name = "commentId") Long commentId, @RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto commentResponseDto = commentService.updateComment(commentRequestDto, commentId,token);
        return commentResponseDto;
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<CommonResponseDto> deleteComment(@RequestHeader(value = "Authorization") String token, @PathVariable(name = "commentId") Long commentId) {
        try {
            commentService.deleteComment(commentId,token);
            return ResponseEntity.ok().body(new CommonResponseDto("정상적으로 삭제 되었습니다.", HttpStatus.OK.value()));
        } catch (RejectedExecutionException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}
