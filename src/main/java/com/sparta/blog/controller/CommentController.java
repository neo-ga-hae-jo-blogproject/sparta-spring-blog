package com.sparta.blog.controller;

import com.sparta.blog.dto.CommentRequestDto;
import com.sparta.blog.dto.CommentResponseDto;
import com.sparta.blog.commonDto.CommonResponseDto;
import com.sparta.blog.security.UserDetailsImpl;
import com.sparta.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RejectedExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards/{boardId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public CommentResponseDto review(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable(name = "boardId") Long boardId, @RequestBody CommentRequestDto commentRequestDto) {
        return commentService.review(commentRequestDto, boardId, userDetails);
    }

    @PutMapping("/{commentId}/update")
    public CommentResponseDto updateComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable(name = "commentId") Long commentId, @RequestBody CommentRequestDto commentRequestDto, @PathVariable(name = "boardId") Long boardId) {
        return commentService.updateComment(commentRequestDto, boardId, commentId,userDetails);
    }

    @DeleteMapping("/{commentId}/delete")
    public ResponseEntity<CommonResponseDto> deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable(name = "commentId") Long commentId, @PathVariable(name = "boardId") Long boardId) {
        try {
            commentService.deleteComment(boardId,commentId,userDetails);
            return ResponseEntity.ok().body(new CommonResponseDto("정상적으로 삭제 되었습니다.", HttpStatus.OK.value()));
        } catch (RejectedExecutionException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}
