package com.sparta.blog.dto;

import com.sparta.blog.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private String email;
    private String username;
    private String info;

    public UserResponseDto(User user) {
        this.email=user.getEmail();
        this.username=user.getUsername();
        this.info=user.getInfo();
    }
}
