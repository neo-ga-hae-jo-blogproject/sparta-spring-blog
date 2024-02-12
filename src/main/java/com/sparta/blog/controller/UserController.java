package com.sparta.blog.controller;

import com.sparta.blog.dto.UserRequestDto;
import com.sparta.blog.dto.CommonResponseDto;
import com.sparta.blog.jwt.JwtUtil;
import com.sparta.blog.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RequestMapping("/api/users")
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


    @PostMapping("/signin")
    public ResponseEntity<CommonResponseDto> login(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response, HttpServletRequest request) {
        try {
            // 이미 로그인된 사용자인지 확인
            if (userService.isUserLoggedIn(userRequestDto.getEmail(), request)) {
                return ResponseEntity.badRequest().body(new CommonResponseDto("이미 로그인된 사용자입니다.", HttpStatus.BAD_REQUEST.value()));
            }

            userService.login(userRequestDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }

        response.setHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(userRequestDto.getEmail()));

        return ResponseEntity.ok().body(new CommonResponseDto("로그인 성공", HttpStatus.OK.value()));
    }

    @PostMapping("/signout")
    public ResponseEntity<CommonResponseDto> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("loggedInUser") != null) {
            // 세션에서 로그인 정보를 제거
            session.removeAttribute("loggedInUser");
            return ResponseEntity.ok(new CommonResponseDto("로그아웃이 완료되었습니다.", HttpStatus.OK.value()));
        } else {
            return ResponseEntity.badRequest().body(new CommonResponseDto("로그인 상태가 아닙니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }

}
