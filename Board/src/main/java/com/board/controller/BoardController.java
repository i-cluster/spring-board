package com.board.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.board.constant.Method;
import com.board.domain.BoardDTO;
import com.board.service.BoardService;
import com.board.util.UiUtils;

@Controller
public class BoardController extends UiUtils {

	@Autowired
	private BoardService boardService;
	
	@GetMapping(value = "/board/write.do")				// 기존의 RequestMapping을 대체
	public String openBoardWrite(@ModelAttribute("params") BoardDTO params, @RequestParam(value = "idx", required = false) Long idx, Model model) {

		// 인덱스 값이 없으면 새 객체를 만들어 전달
		if (idx == null) {
			// model에 데이터를 담아 View로 전송
			model.addAttribute("board", new BoardDTO());
		}
		// 값이 있으면 해당 id의 글 정보를 담은 객체를 전달
		else {
			BoardDTO board = boardService.getBoardDetail(idx);
			if (board == null || "Y".equals(board.getDeleteYn())) {
				return showMessageWithRedirect("존재하지 않는 게시물입니다", "/board/list.do", Method.GET, null, model);
			}
			
			model.addAttribute("board", board);
		}
		
		// void, String, ModelAndView, Map, List 등 사용 가능
		return "board/write";				 // HTML 경로(String이나 ModelAndView 사용)
	}
	
	@PostMapping(value = "/board/register.do")
	public String registerBoard(@ModelAttribute("params") final BoardDTO params, Model model) {
		Map<String, Object> pagingParams = getPagingParams(params);
		try {
			boolean isRegistered = boardService.registerBoard(params);
			if (isRegistered == false) {
				// 등록 실패 메시지
				return showMessageWithRedirect("등록 실패", "/board/list.do", Method.GET, pagingParams, model);
			}
		} catch (DataAccessException e) {
			// 데이터베이스 처리 에러 메시지
			return showMessageWithRedirect("데이터베이스 처리 에러", "/board/list.do", Method.GET, pagingParams, model);
		} catch (Exception e) {
			// 시스템 에러 메시지
			return showMessageWithRedirect("시스템 에러", "/board/list.do", Method.GET, pagingParams, model);
		}
		
		return showMessageWithRedirect("등록 완료", "/board/list.do", Method.GET, pagingParams, model);
	}
	
	@GetMapping(value = "/board/list.do")
	public String openBoardList(@ModelAttribute("params") BoardDTO params, Model model) {				// @ModelAttribute: 파라미터 객체를 View로 전달
		List<BoardDTO> boardList = boardService.getBoardList(params);
		model.addAttribute("boardList", boardList);
		
		return "board/list";
	}
	
	@GetMapping(value = "/board/detail.do")
	public String openBoardDetail(@ModelAttribute("params") BoardDTO params, @RequestParam(value = "idx", required = false) Long idx, Model model) {
		// 인덱스 값이 없으면 글 목록으로 돌아가기
		if (idx == null) {
			return showMessageWithRedirect("올바르지 않은 접근입니다", "/board/list.do", Method.GET, null, model);
		}
		
		BoardDTO board = boardService.getBoardDetail(idx);
		
		// 삭제 or 비밀 글이면 글 목록으로 돌아가기
		if (board == null || "Y".equals(board.getDeleteYn())) {
			return showMessageWithRedirect("존재하지 않는 게시물입니다", "/board/list.do", Method.GET, null, model);
		}
		model.addAttribute("board", board);
		
		return "board/detail";
	}
	
	@PostMapping(value = "/board/delete.do")
	public String deleteBoard(@ModelAttribute("params") BoardDTO params, @RequestParam(value = "idx", required = false) Long idx, Model model) {
		if (idx == null) {
			return showMessageWithRedirect("올바르지 않은 접근입니다", "/board/list.do", Method.GET, null, model);
		}
		
		Map<String, Object> pagingParams = getPagingParams(params);
		try {
			boolean isDeleted = boardService.deleteBoard(idx);
			if (isDeleted == false) {
				// 삭제 실패
				return showMessageWithRedirect("삭제 실패", "/board/list.do", Method.GET, pagingParams, model);
			}
		} catch (DataAccessException e) {
			// TODO: 데이터 접근 에러
			return showMessageWithRedirect("데이터 처리 에러", "/board/list.do", Method.GET, pagingParams, model);
		} catch (Exception e) {
			// TODO: 시스템 에러
			return showMessageWithRedirect("시스템 에러", "/board/list.do", Method.GET, pagingParams, model);
		}
		
		return showMessageWithRedirect("삭제 완료", "/board/list.do", Method.GET, pagingParams, model);
	}
}
