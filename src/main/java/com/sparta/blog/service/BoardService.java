package com.sparta.blog.service;

import com.sparta.blog.dto.BoardRequestDto;
import com.sparta.blog.dto.BoardResponseDto;
import com.sparta.blog.entity.Board;
import com.sparta.blog.entity.User;
import com.sparta.blog.jwt.JwtUtil;
import com.sparta.blog.repository.BoardRepository;
import com.sparta.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    @Transactional
    public BoardResponseDto createBoard(String accessToken, BoardRequestDto requestDto) {
        String email = jwtUtil.getEmailFromToken(accessToken);
        User user = findUserBy(email);
        Board board = new Board(requestDto, user);

        return new BoardResponseDto(boardRepository.save(board));
    }

    private User findUserBy(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new NoSuchElementException("사용자를 찾을 수 없습니다.")
        );
    }

    @Transactional
    public BoardResponseDto updateBoard(String accessToken, Long id, BoardRequestDto requestDto) {
        String email = jwtUtil.getEmailFromToken(accessToken);
        User user = findUserBy(email);
        Board board = getBoardByEmail(user, id);
        board.update(requestDto);


        return new BoardResponseDto(board);
    }

    private Board getBoardByEmail(User user, Long id) {
        return user.getBoards().stream()
                .filter(
                        boards -> boards.getBoardId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("작성자만 삭제/수정 할 수 있습니다.")
                );
    }

    @Transactional
    public String deleteBoard(String accessToken, Long id) {
        String email = jwtUtil.getEmailFromToken(accessToken);
        User user = findUserBy(email);
        Board board = getBoardByEmail(user, id);

        boardRepository.delete(board);

        return "게시글 삭제 완료";
    }
}










