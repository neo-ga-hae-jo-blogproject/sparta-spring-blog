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
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public Comment(String content, Long boardId,User user) {
        this.content = content;
        this.boardId = boardId;
        this.user = user;
    }

    public void updateContent(String updateContent) {
        this.content = updateContent;
    }

    public String getUsername(){
        return user.getUsername();
    }
}
