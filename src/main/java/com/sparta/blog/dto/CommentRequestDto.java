package com.sparta.blog.dto;

import lombok.Getter;
import org.hibernate.annotations.Filter;

@Getter
public class CommentRequestDto {
    private String content;
}
