package com.board.service;

import java.util.List;

import com.board.domain.BoardDTO;

public interface BoardService {
	
	public boolean registerBoard(BoardDTO params);
	
	public BoardDTO getBoardDetail(Long id);
	
	public boolean deleteBoard(Long id);
	
	public List<BoardDTO> getBoardList();
	
}
