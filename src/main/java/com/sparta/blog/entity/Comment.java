package com.sparta.blog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "commnets")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long boardId;

    @Column(nullable = false)
    private Long userId;

    public Comment(String content, Long boardId) {
        this.content = content;
        this.boardId = boardId;
    }

    public void updateContent(String updateContent) {
        this.content = updateContent;
    }
}
