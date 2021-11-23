package com.board;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import com.board.domain.BoardDTO;
import com.board.mapper.BoardMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
public class MapperTests {
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Test
	public void testOfCreate() {
		BoardDTO params = new BoardDTO();
		params.setTitle("1번 글 제목");
		params.setContent("1번 글 내용");
		params.setWriter("테스터");
		
		int result = boardMapper.createBoard(params);
		System.out.println("결과는 " + result);
	}
	
	@Test
	public void testOfSelectDetail() {
		BoardDTO board = boardMapper.selectBoardDetail((long) 1);
		try {
			// Jackson 라이브러리를 이용해 board 정보를 JSON 문자열로 변환
			ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			String boardJson = mapper.writeValueAsString(board);
			
			System.out.println("====================");
			System.out.println(boardJson);
			System.out.println("====================");
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testOfUpdate() {
		BoardDTO params = new BoardDTO();
		params.setTitle("수정된 1번 글 제목");
		params.setContent("수정된 1번 글 내용");
		params.setWriter("수정자");
		params.setIdx((long) 1);
		
		int result = boardMapper.updateBoard(params);
		if (result == 1) {
			BoardDTO board = boardMapper.selectBoardDetail((long) 1);
			try {
				ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
				String boardJson = mapper.writeValueAsString(board);
				
				System.out.println("====================");
				System.out.println(boardJson);
				System.out.println("====================");
				
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void testOfDelete() {
		int result = boardMapper.deleteBoard((long) 1);
		if (result == 1) {
			BoardDTO board = boardMapper.selectBoardDetail((long) 1);
			try {
				ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
				String boardJson = mapper.writeValueAsString(board);
				
				System.out.println("====================");
				System.out.println(boardJson);
				System.out.println("====================");
				
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void testMultipleCreate() {
		for (int i = 1; i <= 50; i++) {
			BoardDTO params = new BoardDTO();
			params.setTitle(i + "번 글 제목");
			params.setContent(i + "번 글 내용");
			params.setWriter(i + "번 테스터");
			
			boardMapper.createBoard(params);
		}
	}
	
	@Test
	public void testSelectList() {
		int boardTotalCount = boardMapper.selectBoardTotalCount();
		if (boardTotalCount > 0) {
			List<BoardDTO> boardList = boardMapper.selectBoardList();
			
			if (CollectionUtils.isEmpty(boardList) == false) {
				for (BoardDTO board : boardList) {
					System.out.println("====================");
					System.out.println(board.getTitle());
					System.out.println(board.getContent());
					System.out.println(board.getWriter());
					System.out.println("====================");
				}
			}
		}
	}
}
