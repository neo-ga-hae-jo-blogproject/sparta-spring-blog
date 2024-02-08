package com.sparta.blog.service;

import com.sparta.blog.dto.BoardRequestDto;
import com.sparta.blog.dto.BoardResponseDto;
import com.sparta.blog.entity.Board;
import com.sparta.blog.entity.User;
import com.sparta.blog.jwt.JwtUtil;
import com.sparta.blog.repository.BoardRepository;
import com.sparta.blog.repository.UserRepository;
import io.jsonwebtoken.Claims;
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

//    @Transactional
//    public BoardResponseDto createBoard(String tokenValue, BoardRequestDto requestDto) {
//        // 사용자 아이디
//        Claims email;
//
//        String token = jwtUtil.substringToken(tokenValue);
//
//        // 토큰 검증 및 정보 가져오기
//        if (jwtUtil.validateToken(token)) {
//            email = jwtUtil.getUserInfoFromToken(token);
//        } else {
//            throw new IllegalArgumentException("검증되지 않은 유저 입니다.");
//        }
//
////        // 유저가 있는지 확인
//        User user = userRepository.findByEmail(email).orElseThrow(
//                () -> new IllegalArgumentException("해당 유저가 없습니다.")
//        );
//
//        Board board = new Board(requestDto.getTitle(), requestDto.getContent(), user);
//
//        Board savedBoard = boardRepository.save(board);
//
//        BoardResponseDto boardResponseDto = new BoardResponseDto(savedBoard.getTitle(), savedBoard.getContent(), email);
//
//        return boardResponseDto;
//   //     return null;
//    }

//    @Transactional
//    public BoardResponseDto updateBoard(String tokenValue, Long boardId, BoardRequestDto requestDto) {
//        // 사용자 아이디
//        Claims email;
//
//        String token = jwtUtil.substringToken(tokenValue);
//
//        // 토큰 검증 및 정보 가져오기
//        if (jwtUtil.validateToken(token)) {
//            email = jwtUtil.getUserInfoFromToken(token);
//        } else {
//            throw new IllegalArgumentException("검증되지 않은 유저 입니다.");
//        }
//
//        // 유저가 있는지 확인
//        User user = userRepository.findByEmail(email).orElseThrow(
//                () -> new IllegalArgumentException("해당 유저가 없습니다.")
//        );
//
//        // 해당 게시글이 DB에 있는지 확인
//        Board board = boardRepository.findById(boardId).orElseThrow(
//                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
//        );
//
//        // 본인이 작성한 글인지 확인
//        if(!board.getUser().getEmail().equals(email)) {
//            throw new IllegalArgumentException("본인이 작성한 글만 수정 가능합니다.");
//        }
//
//        board.update(requestDto);
//
//        return new BoardResponseDto(board);
//     //   return null;
//    }
//
//    @Transactional
//    public String deleteBoard(String tokenValue, Long boardId) {
//        // 사용자 아이디
//        Claims email;
//
//        String token = jwtUtil.substringToken(tokenValue);
//
//        // 토큰 검증 및 정보 가져오기
//        if (jwtUtil.validateToken(token)) {
//            email = jwtUtil.getUserInfoFromToken(token);
//        } else {
//            throw new IllegalArgumentException("검증되지 않은 유저 입니다.");
//        }
//
////        // 유저가 있는지 확인
//        User user = userRepository.findByEmail(email).orElseThrow(
//                () -> new IllegalArgumentException("해당 유저가 없습니다.")
//        );
//
//        // 해당 게시글이 DB에 있는지 확인
//        Board board = boardRepository.findById(boardId).orElseThrow(
//                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
//        );
//
//        // 본인이 작성한 글인지 확인
//        if(!board.getUser().getEmail().equals(email)) {
//            throw new IllegalArgumentException("본인이 작성한 글만 삭제 가능합니다.");
//        }
//
//        boardRepository.delete(board);
//
//        return "게시글 삭제 완료";
//    }
}










