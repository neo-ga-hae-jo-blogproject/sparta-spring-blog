package com.sparta.blog.controller;

import com.sparta.blog.dto.*;
import com.sparta.blog.entity.User;
import com.sparta.blog.jwt.JwtUtil;
import com.sparta.blog.service.UserService;
import jakarta.persistence.Id;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
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


    @PostMapping("/signin")
    public ResponseEntity<CommonResponseDto> login(@RequestBody UserLoginRequestDto userLoginRequestDto, HttpServletResponse response, HttpServletRequest request) {
        try {

            userService.login(userLoginRequestDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        response.setHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(userLoginRequestDto.getEmail()));

        return ResponseEntity.ok().body(new CommonResponseDto("로그인 성공", HttpStatus.OK.value()));
    }

    //조회
    @GetMapping("/{userId}")
    public UserResponseDto getUserList(@PathVariable Long userId){
        return userService.getUserList(userId);
    }
    // info 수정
    @PutMapping("/{userId}/update-info")
    public UserResponseDto updateUserInfo(@RequestHeader("Authorization") String token,
                                          @PathVariable Long userId,
                                          @Valid @RequestBody UserRequestDto userRequestDto) {
        return userService.updateUserInfo(token, userId, userRequestDto);
    }

    // 비밀번호 수정
    @PutMapping("/{userId}/update-password")
    public UserResponseDto updateUserPassword(@RequestHeader("Authorization") String token,
                                                     @PathVariable Long userId,
                                                     @Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto) {

        return userService.updateUserPassword(token,userId,userUpdateRequestDto);
    }
}
