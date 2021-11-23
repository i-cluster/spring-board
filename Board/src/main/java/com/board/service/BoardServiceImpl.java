package com.board.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.board.domain.BoardDTO;
import com.board.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardMapper boardMapper;
	
	@Override
	public boolean registerBoard(BoardDTO params) {
		int queryResult = 0;
		
		if (params.getIdx() == null) {			// 지정 인덱스 값이 없으면 Create, 있으면 Update
			queryResult = boardMapper.createBoard(params);			// 쿼리를 실행한 횟수 저장
		} else {
			queryResult = boardMapper.updateBoard(params);
		}
		
		return (queryResult == 1) ? true : false;		// 실행 결과 리턴
	}
	
	@Override
	public BoardDTO getBoardDetail(Long idx) {
		return boardMapper.selectBoardDetail(idx);
	}
	
	@Override
	public boolean deleteBoard(Long idx) {
		int queryResult = 0;
		
		BoardDTO board = boardMapper.selectBoardDetail(idx);
		System.out.println(board.getDeleteYn());
		
		if (board != null && "N".equals(board.getDeleteYn())) {			// 글이 존재하고 삭제 상태가 아닐 경우 실행
			queryResult = boardMapper.deleteBoard(idx);
		}
		
		return (queryResult == 1) ? true : false;		// 실행 결과 리턴
	}
	
	@Override
	public List<BoardDTO> getBoardList() {
		List<BoardDTO> boardList = Collections.emptyList();			// NPE 방지
		
		int boardTotalCount = boardMapper.selectBoardTotalCount();
		
		if (boardTotalCount > 0) {
			boardList = boardMapper.selectBoardList();
		}
		
		return boardList;
	}
	
}
