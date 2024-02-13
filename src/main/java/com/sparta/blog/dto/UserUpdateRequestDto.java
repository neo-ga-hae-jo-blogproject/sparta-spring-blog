package com.sparta.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequestDto {
    @NotBlank(message = "비밀번호는 필수로 입력해야 합니다.")
    private String oldPassword;

    @NotBlank(message = "비밀번호는 필수로 입력해야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$")
    private String newPassword;

    @NotBlank(message = "비밀번호는 필수로 입력해야 합니다.")
    private String newPasswordCheck;
}
