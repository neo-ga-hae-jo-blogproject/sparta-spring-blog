package com.sparta.blog.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentRequestDto {
    @NotBlank(message = "내용을 필수로 입력해야 합니다.")
    private String content;
}
