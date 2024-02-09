package com.sparta.blog.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    @Pattern(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9.-]+\\.(com)$")
    private String email;

    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$")
    private String password;

    private String username;

    private String info;

}
