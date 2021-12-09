package com.board.domain;

import java.time.LocalDateTime;

import com.board.paging.Criteria;
import com.board.paging.PaginationInfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonDTO extends Criteria {
	
	// Pagination 정보
	private PaginationInfo paginationInfo;
	
	// 삭제 여부
	private String deleteYn;
	
	// 등록일
	private LocalDateTime createTm;
	
	// 수정일
	private LocalDateTime updateTm;
	
	// 삭제일
	private LocalDateTime deleteTm;
	
}
