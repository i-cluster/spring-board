package com.board.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.board.adapter.GsonLocalDateTimeAdapter;
import com.board.domain.CommentDTO;
import com.board.service.CommentService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@RestController
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@RequestMapping(value = { "/comments", "/comments/{idx}" }, method = { RequestMethod.POST, RequestMethod.PATCH } )
	public JsonObject registerComment(@PathVariable(value = "idx", required = false) Long idx, @RequestBody final CommentDTO params) {
		
		JsonObject jsonObj = new JsonObject();
		
		try {
//			if (idx != null) {
//				params.setIdx(idx);
//			}
			
			boolean isRegistered = commentService.registerComment(params);
			jsonObj.addProperty("result", isRegistered);
			
		} catch (DataAccessException e) {
			// TODO: handle exception
			jsonObj.addProperty("message", "데이터 처리 에러");
		} catch (Exception e) {
			// TODO: handle exception
			jsonObj.addProperty("message", "시스템 에러");
		}
		
		return jsonObj;
	}
	
	@GetMapping(value = "/comments/{boardIdx}")				// @PathVariable 지정 변수와 바인딩
	public JsonObject getCommentList(@PathVariable("boardIdx") Long boardIdx, @ModelAttribute("params") CommentDTO params) {
		
		// JSON 객체 생성
		JsonObject jsonObj = new JsonObject();
		
		List<CommentDTO> commentList = commentService.getCommentList(params);
		if (CollectionUtils.isEmpty(commentList) == false) {
			
			// 어댑터 클래스 포함하여 JsonArrary 타입으로 변환
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).create();
			JsonArray jsonArr = gson.toJsonTree(commentList).getAsJsonArray();
			
			// 객체에 프로퍼티 추가
			jsonObj.add("commentList", jsonArr);
		}
		
		return jsonObj;
	}
}