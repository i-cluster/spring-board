package com.board.paging;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationInfo {
	
	// 파라미터 클래스
	private Criteria criteria;
	
	// 전체 데이터 수
	private int totalRecordCount;
	
	// 전체 페이지 수
	private int totalPageCount;
	
	// 첫 페이지 번호
	private int firstPage;
	
	// 마지막 페이지 번호
	private int lastPage;
	
	// SQL 조건절의 첫 RNUM
	private int firstRecordIndex;
	
	// SQL 조건절의 마지막 RNUM
	private int lastRecordIndex;
	
	// 이전 페이지 여부
	private boolean hasPreviousPage;
	
	// 다음 페이지 여부
	private boolean hasNextPage;
	
	public PaginationInfo(Criteria criteria) {
		if (criteria.getCurrentPageNo() < 1) {
			criteria.setCurrentPageNo(1);
		}
		
		if (criteria.getRecordsPerPage() < 1 || criteria.getRecordsPerPage() > 100) {
			criteria.setRecordsPerPage(10);
		}
		
		if (criteria.getPageSize() < 5 || criteria.getPageSize() > 20) {
			criteria.setPageSize(10);
		}
		
		this.criteria = criteria;
	}
	
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
		
		if (totalRecordCount > 0) {
			calculation();
		}
	}
	
	private void calculation() {
		
		// 전체 페이지 수
		totalPageCount = ((totalRecordCount - 2) / criteria.getRecordsPerPage()) + 1;
		if (criteria.getCurrentPageNo() > totalPageCount) {
			criteria.setCurrentPageNo(totalPageCount);
		}
		
		// 첫 페이지 번호
		firstPage = ((criteria.getCurrentPageNo() - 1) / criteria.getPageSize()) * criteria.getPageSize() + 1;
		
		// 마지막 페이지 번호
		lastPage = firstPage + criteria.getPageSize() - 1;
		if (lastPage > totalPageCount) {
			lastPage = totalPageCount;
		}
		
		// SQL 조건절 시작 RNUM
		firstRecordIndex = (criteria.getCurrentPageNo() - 1) * criteria.getRecordsPerPage();
		
		// SQL 조건절 마지막 RNUM
		lastRecordIndex = criteria.getCurrentPageNo() * criteria.getRecordsPerPage();
		
		// 이전 페이지 여부
		hasPreviousPage = firstPage != 1;
		
		// 다음 페이지 여부
		hasNextPage = (lastPage * criteria.getRecordsPerPage()) < totalRecordCount;
	}
	
}










