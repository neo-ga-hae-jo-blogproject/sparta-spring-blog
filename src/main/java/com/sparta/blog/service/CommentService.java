package com.sparta.blog.service;

import com.sparta.blog.dto.CommentRequestDto;
import com.sparta.blog.dto.CommentResponseDto;
import com.sparta.blog.entity.Board;
import com.sparta.blog.entity.Comment;
import com.sparta.blog.entity.User;
import com.sparta.blog.jwt.JwtUtil;
import com.sparta.blog.repository.BoardRepository;
import com.sparta.blog.repository.CommentRepository;
import com.sparta.blog.repository.UserRepository;
import com.sparta.blog.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final JwtUtil jwtUtil;



    public CommentResponseDto review(CommentRequestDto commentRequestDto, Long boardId, UserDetailsImpl userDetails){
        Long userId = userDetails.getUser().getId();
        User user = userRepository.findById(userId).orElseThrow();
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NoSuchElementException("없는 게시물 입니다."));

        Comment comment = new Comment(commentRequestDto.getContent(), board, user);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }


    public CommentResponseDto updateComment(CommentRequestDto commentRequestDto, Long boardId,Long commentId,UserDetailsImpl userDetails){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NoSuchElementException("등록된 댓글이 아닙니다."));
        User user = userDetails.getUser();
        isCommentInBoard(boardId,comment);

        //자신이 작성한 댓글인지 아닌지 확인
        isCommentMyselfValidate(user, comment);

        comment.updateContent(commentRequestDto.getContent());
        return new CommentResponseDto(comment);
    }


    public void deleteComment(Long boardId,Long commentId, UserDetailsImpl userDetails){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NoSuchElementException("등록된 댓글이 아닙니다."));
        User user = userDetails.getUser();
        isCommentInBoard(boardId,comment);
        isCommentMyselfValidate(user, comment);

        commentRepository.delete(comment);
    }
    private void isCommentInBoard(Long boardId, Comment comment) {
        if(!Objects.equals(comment.getBoard().getId(), boardId)){
            throw new AccessDeniedException("게시물의 댓글이 아닙니다.");
        }
    }

    private void isCommentMyselfValidate(User user, Comment comment) {
        if(!(user.getId().equals(comment.getUser().getId()))){
            throw new AccessDeniedException("자신이 작성한 댓글이 아닙니다.");
        }
    }


}
