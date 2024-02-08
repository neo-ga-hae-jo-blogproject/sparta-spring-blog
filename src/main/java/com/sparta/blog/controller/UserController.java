package com.sparta.blog.controller;

import com.sparta.blog.dto.UserRequestDto;
import com.sparta.blog.dto.CommonResponseDto;
import com.sparta.blog.jwt.JwtUtil;
import com.sparta.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@Valid @RequestBody UserRequestDto userRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new CommonResponseDto("이메일 형식이 유효하지 않습니다.", HttpStatus.BAD_REQUEST.value()));
        }
        try {
            userService.signup(userRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED.value())
                    .body(new CommonResponseDto("회원가입 성공", HttpStatus.CREATED.value()));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(new CommonResponseDto("중복된 이메일 입니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }





}
