package com.sparta.blog.service;

import com.sparta.blog.dto.*;
import com.sparta.blog.entity.Board;
import com.sparta.blog.entity.User;
import com.sparta.blog.jwt.JwtUtil;
import com.sparta.blog.repository.BoardRepository;
import com.sparta.blog.repository.CommentRepository;
import com.sparta.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public BoardResponseDto createBoard(String accessToken, BoardRequestDto requestDto) {
        User user = findByToken(accessToken);
        Board board = new Board(requestDto, user);

        return new BoardResponseDto(boardRepository.save(board));
    }

    private User findByToken(String accessToken) {
        String email = jwtUtil.getEmailFromToken(accessToken);

        return userRepository.findByEmail(email).orElseThrow(
                () -> new NoSuchElementException("사용자를 찾을 수 없습니다.")
        );
    }

    @Transactional
    public BoardResponseDto updateBoard(String accessToken, Long id, BoardRequestDto requestDto) {
        User user = findByToken(accessToken);
        Board board = getBoardByUser(user, id);
        board.update(requestDto);

        return new BoardResponseDto(board);
    }

    private Board getBoardByUser(User user, Long id) {
        return user.getBoards().stream()
                .filter(
                        boards -> boards.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("작성자만 삭제/수정 할 수 있습니다.")
                );
    }

    @Transactional
    public String deleteBoard(String accessToken, Long id) {
        User user = findByToken(accessToken);
        Board board = getBoardByUser(user, id);

        boardRepository.delete(board);

        return "게시글 삭제 완료";
    }


    // 게시물 전체 목록 조회
    public List<BoardListResponseDto> getBoardList() {
        return boardRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(board -> new BoardListResponseDto(board, new ArrayList<>()))
                .collect(Collectors.toList());
    }

    // 특정 게시물 조회
    public BoardListResponseDto getBoardDetail(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("글이 존재하지 않습니다.")
        );
        List<CommentResponseDto> commentList = commentRepository.findAllByBoardId(id)
                .stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
        return new BoardListResponseDto(board, commentList);
    }
}










