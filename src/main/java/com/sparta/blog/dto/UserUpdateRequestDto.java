package com.sparta.blog.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequestDto {

    private String oldPassword;

    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$")
    private String newPassword;

    private String newPasswordCheck;
}
