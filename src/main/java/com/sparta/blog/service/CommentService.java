package com.sparta.blog.service;

import com.sparta.blog.dto.CommentRequestDto;
import com.sparta.blog.dto.CommentResponseDto;
import com.sparta.blog.entity.Comment;
import com.sparta.blog.entity.User;
import com.sparta.blog.jwt.JwtUtil;
import com.sparta.blog.repository.CommentRepository;
import com.sparta.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    public CommentResponseDto review(CommentRequestDto commentRequestDto,Long boardId,String token){
        User user = getUserByToken(token);

        Comment comment = new Comment(commentRequestDto.getContent(), boardId, user);
        commentRepository.save(comment);
        return new CommentResponseDto(comment.getContent(), comment.getUsername());
    }


    public CommentResponseDto updateComment(CommentRequestDto commentRequestDto,Long commentId, String token){
        Comment comment = commentRepository.findById(commentId).orElseThrow();

        User user = getUserByToken(token);

        //자신이 작성한 댓글인지 아닌지 확인
        isCommentMyself(user, comment);

        comment.updateContent(commentRequestDto.getContent());
        return new CommentResponseDto(comment.getContent(), comment.getUsername());
    }

    public void deleteComment(Long commentId,String token){
        Comment comment = commentRepository.findById(commentId).orElseThrow();

        User user = getUserByToken(token);

        isCommentMyself(user, comment);

        commentRepository.delete(comment);
    }

    private static void isCommentMyself(User user, Comment comment) {
        if(!user.equals(comment.getUser())){
            throw new IllegalArgumentException("자신이 작성한 댓글이 아닙니다.");
        }
    }

    private User getUserByToken(String token) {
        String email = jwtUtil.getEmailFromToken(token);
        User user = userRepository.findByEmail(email).orElseThrow();
        return user;
    }



}
