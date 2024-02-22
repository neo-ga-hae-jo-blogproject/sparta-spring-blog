package com.sparta.blog.service;

import com.sparta.blog.dto.BoardRequestDto;
import com.sparta.blog.dto.BoardResponseDto;
import com.sparta.blog.entity.Board;
import com.sparta.blog.entity.User;
import com.sparta.blog.repository.BoardRepository;
import com.sparta.blog.repository.CommentRepository;
import com.sparta.blog.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 가정 :
 * User는 아이디 값이 1L,2L을 가지고 있다. : 데이터 베이스에 넣음
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 서버의 PORT 를 랜덤으로 설정합니다.
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 테스트 인스턴스의 생성 단위를 클래스로 변경합니다. // 절차지향처럼 가능
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BoardServiceTest {
    @Autowired
    BoardService boardService;
    @Autowired
    UserRepository userRepository;

    User user1;

    BoardResponseDto createBoard = null;

    @BeforeAll
    void before(){
        user1 = userRepository.findById(1L).orElse(null);
    }

    @Test
    @Order(1)
    @DisplayName("게시물 생성")
    void createBoard(){
        //given
        String title = "제목";
        String content = "내용입니다.";

        BoardRequestDto boardRequestDto = new BoardRequestDto();
        boardRequestDto.setTitle(title);
        boardRequestDto.setContent(content);
        //when
        BoardResponseDto boardResponseDto = boardService.createBoard(user1, boardRequestDto);

        //then
        assertEquals(boardResponseDto.getTitle(),title);
        assertEquals(boardResponseDto.getContent(),content);

        createBoard = boardResponseDto;
        System.out.println("createBoard = " + createBoard);
    }
    @Test
    @Order(2)
    @DisplayName("게시물 제목 및 내용 수정")
    void updateBoard(){

        //given
        Long boardId = createBoard.getId();
        String title = "제목도 수정";
        String content = "내용이 수정되었습니다.";
        BoardRequestDto boardRequestDto = new BoardRequestDto();
        boardRequestDto.setTitle(title);
        boardRequestDto.setContent(content);

        //when
        BoardResponseDto board = boardService.updateBoard(user1,boardId, boardRequestDto);

        //then
        assertEquals(board.getTitle(),title);
        assertEquals(board.getContent(),content);
    }
    @Test
    @Order(3)
    @DisplayName("작성자가 아닌 사람이 삭제,수정하려고 하면 예외 발생")
    void updateAndDeleteBoardByDifferentUser(){
        //given
        Long boardIdOfUser1 = createBoard.getId();
        String title = "제목도 수정";
        String content = "내용이 수정되었습니다.";
        BoardRequestDto boardRequestDto = new BoardRequestDto();
        boardRequestDto.setTitle(title);
        boardRequestDto.setContent(content);
        User user2 = userRepository.findById(2L).orElse(null);
        //when
        //then
        assertThrows(org.springframework.security.access.AccessDeniedException.class, () -> boardService.updateBoard(user2, boardIdOfUser1, boardRequestDto));
        assertThrows(org.springframework.security.access.AccessDeniedException.class, () -> boardService.deleteBoard(user2, boardIdOfUser1));
    }
    @Test
    @Order(4)
    @DisplayName("게시물 삭제")
    void deleteBoard(){
        //given
        Long boardId = createBoard.getId();
        String deleteMessage = "게시글 삭제 완료";
        //when
        String Message = boardService.deleteBoard(user1, boardId);
        //then
        assertEquals(deleteMessage,Message);
    }
}
