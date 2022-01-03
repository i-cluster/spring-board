package com.board;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.board.domain.CommentDTO;
import com.board.service.CommentService;

@SpringBootTest
public class CommentTests {
	
	@Autowired
	private CommentService commentService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Test
	public void registerComments() {
		int number = 20;
		
		for (int i = 1; i <= number; i++) {
			CommentDTO params = new CommentDTO();
			params.setBoardIdx((long) 50);
			params.setContent(i + "번 댓글");
			params.setWriter(i + "번 작성자");
			commentService.registerComment(params);
		}
		
		logger.debug("댓글 " + number + "개 등록 완료");
	}
	
	@Test
	public void deleteComment() {
		commentService.deleteComment((long) 20);
		
		getCommentList();
	}
	
	@Test
	public void getCommentList() {
		CommentDTO params = new CommentDTO();
		params.setBoardIdx((long) 50);
		
		commentService.getCommentList(params);
	}
}
