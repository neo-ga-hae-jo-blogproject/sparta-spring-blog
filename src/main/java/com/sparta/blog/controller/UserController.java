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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    

    @PostMapping("/sign-up")
    public ResponseEntity<Object> signUp(@Valid @RequestBody UserRequestDto userRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new CommonResponseDto("이메일 형식이 유효하지 않습니다.", HttpStatus.BAD_REQUEST.value()));
        }
        try {
            userService.signUp(userRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED.value())
                    .body(new CommonResponseDto("회원가입 성공", HttpStatus.CREATED.value()));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(new CommonResponseDto("중복된 이메일입니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }


    @PostMapping("/sign-in")
    public ResponseEntity<CommonResponseDto> signIn(@RequestBody UserLoginRequestDto userLoginRequestDto, HttpServletResponse response, HttpServletRequest request) {
        try {
            // 이미 로그인된 사용자인지 확인
            if (userService.isUserLoggedIn(userLoginRequestDto.getEmail(), request)) {
                return ResponseEntity.badRequest().body(new CommonResponseDto("이미 로그인된 사용자입니다.", HttpStatus.BAD_REQUEST.value()));
            }

            userService.signIn(userLoginRequestDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }

        response.setHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(userLoginRequestDto.getEmail()));

        return ResponseEntity.ok().body(new CommonResponseDto("로그인 성공", HttpStatus.OK.value()));
    }

    @PostMapping("/sign-out")
    public ResponseEntity<CommonResponseDto> signOut(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("loggedInUser") != null) {
            // 세션에서 로그인 정보를 제거
            session.removeAttribute("loggedInUser");
            return ResponseEntity.ok(new CommonResponseDto("로그아웃이 완료되었습니다.", HttpStatus.OK.value()));
        } else {
            return ResponseEntity.badRequest().body(new CommonResponseDto("로그인 상태가 아닙니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }

    //조회
    @GetMapping("/{id}")
    public UserResponseDto getUserList(@PathVariable Long id){
        return userService.getUserList(id);
    }
    // info 수정
    @PutMapping("/{id}/update-info")
    public UserResponseDto updateUserInfo(@RequestHeader("Authorization") String token,
                                          @PathVariable Long id,
                                          @Valid @RequestBody UserRequestDto userRequestDto) {
        return userService.updateUserInfo(token, id, userRequestDto);
    }

    // 비밀번호 수정
    @PutMapping("/{id}/update-password")
    public UserResponseDto updateUserPassword(@RequestHeader("Authorization") String token,
                                                     @PathVariable Long id,
                                                     @Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto) {

        return userService.updateUserPassword(token, id, userUpdateRequestDto);
    }
}
