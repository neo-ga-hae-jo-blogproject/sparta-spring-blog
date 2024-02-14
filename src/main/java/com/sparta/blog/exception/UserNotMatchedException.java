package com.sparta.blog.exception;

public class UserNotMatchedException extends RuntimeException {
    public UserNotMatchedException() {
        super("사용자가 일치하지 않습니다.");
    }

}
