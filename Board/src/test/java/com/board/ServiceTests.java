package com.board;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.board.domain.BoardDTO;
import com.board.service.BoardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
public class ServiceTests {
	
	@Autowired
	private BoardService boardService;
	
	@Test
	public void testOfRegister() {
		BoardDTO params = new BoardDTO();
		params.setIdx((long) 51);
		params.setTitle("51번 글 제목");
		params.setContent("51번 글 내용");
		params.setWriter("51번 테스터");
		params.setNoticeYn("");
		
		boolean result = boardService.registerBoard(params);
		System.out.println(result);
	}
	
	@Test
	public void testOfGetBoardDetail() {
		BoardDTO board = boardService.getBoardDetail((long) 1);
		
		try {
			ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			String boardJson = mapper.writeValueAsString(board);
			
			System.out.println(boardJson);
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testOfDeleteBoard() {
		boolean result = boardService.deleteBoard((long) 50);
		
		System.out.println(result);
	}
	
	@Test
	public void testOfGetBoardList() {
		List<BoardDTO> boardList = boardService.getBoardList();
		
		for (BoardDTO board : boardList) {
			try {
				ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
				String boardJson = mapper.writeValueAsString(board);
				
				System.out.println("====================");
				System.out.println(boardJson);
				System.out.println("====================");
				
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
