package org.zerock.domain;

import lombok.Getter;
import lombok.ToString;

// 페이징 처리를 위한 클래스 : 시작페이지, 끝 페이지, 이전 페이지, 다음 페이지

@Getter
@ToString
public class PageDTO {
	
	private int startPage;
	private int endPage;
	private boolean prev;
	private boolean next;
	
	private int total;
	private Criteria cri;
	
	
	public PageDTO(Criteria cri, int total) {
		this.cri = cri;
		this.total = total;
		
		this.endPage = (int)(Math.ceil(cri.getPageNum() / 10.0)) * 10;
		this.startPage = this.endPage - 9;
		
		int realEnd = (int) (Math.ceil(total * 1.0) / cri.getAmount());
		
		if(realEnd < this.endPage) {
			this.endPage = realEnd;
		}
		
		this.prev = this.startPage > 1;
		this.next = this.endPage < realEnd;
		
		
	}
	
	
	
	
}
