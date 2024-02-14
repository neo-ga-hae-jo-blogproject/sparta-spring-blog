package com.sparta.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    @NotBlank(message = "이메일은 필수로 입력해야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9.-]+\\.(com)$", message = "유효하지 않은 이메일 형식입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수로 입력해야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$")
    private String password;

    @NotBlank(message = "이름은 필수로 입력해야 합니다.")
    private String username;

    private String info;
}