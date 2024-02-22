package com.sparta.blog.service;

import com.sparta.blog.dto.*;
import com.sparta.blog.entity.Board;
import com.sparta.blog.entity.User;
import com.sparta.blog.repository.BoardRepository;
import com.sparta.blog.repository.CommentRepository;
import com.sparta.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
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

    @Transactional
    public BoardResponseDto createBoard(User user, BoardRequestDto requestDto) {
        return new BoardResponseDto(boardRepository.save(new Board(requestDto, user)));
    }


    @Transactional
    public BoardResponseDto updateBoard(User user, Long boardId, BoardRequestDto requestDto) {
        Long userId = user.getId();
        Board board = getBoardByUserId(userId, boardId);
        board.update(requestDto);

        return new BoardResponseDto(board);
    }


    @Transactional
    public String deleteBoard(User user, Long id) {
        Long userId = user.getId();
        Board board = getBoardByUserId(userId, id);

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
                () -> new NoSuchElementException("게시글이 존재하지 않습니다.")
        );
        List<CommentResponseDto> commentList = commentRepository.findAllByBoardId(id)
                .stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
        return new BoardListResponseDto(board, commentList);
    }
    private Board getBoardByUserId(Long userId,Long boardId){
        User user = userRepository.findById(userId).orElseThrow();
        Board board = boardRepository.findById(boardId).orElseThrow();
        List<Board> boards = user.getBoards();
        if(!boards.contains(board)) {
            throw new AccessDeniedException("작성자만 삭제/수정 할 수 있습니다.");
        }
        return board;
    }
}










