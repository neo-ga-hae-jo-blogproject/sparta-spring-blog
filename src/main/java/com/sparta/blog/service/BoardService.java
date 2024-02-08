package com.sparta.blog.service;

import com.sparta.blog.dto.BoardResponseDto;
import com.sparta.blog.dto.CommentResponseDto;
import com.sparta.blog.entity.Board;
import com.sparta.blog.repository.BoardRepository;
import com.sparta.blog.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;


    // 게시물 전체 목록 조회
    @Transactional
    public List<BoardResponseDto> getBoardList() {
        List<Board> boards = boardRepository.findAllByOrderByCreatedAtDesc();

        List<BoardResponseDto> boardResponseDtos = boards.stream()
                .map(board -> {
                    List<CommentResponseDto> commentList = board.getCommentList()
                            .stream()
                            .map(CommentResponseDto::new)
                            .collect(Collectors.toList());
                    return new BoardResponseDto(board, commentList);
                })
                .collect(Collectors.toList());

        return boardResponseDtos;
    }


    // 특정 게시물 조회
    @Transactional
    public BoardResponseDto getBoardDetail(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("글이 존재하지 않습니다.")
        );
        List<CommentResponseDto> commentList = commentRepository.findById(id)
                .stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
        return new BoardResponseDto(board,commentList);
    }

}
