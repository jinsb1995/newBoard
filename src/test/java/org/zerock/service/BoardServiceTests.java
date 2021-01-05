package org.zerock.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardServiceTests {
	
	@Setter(onMethod_ = @Autowired)
	private BoardService service;
	
	
//	@Test
//	public void testExist() {
//		
//		System.out.println(service);
//		assertNotNull(service);
//	}
//	
	
//	@Test
//	public void testRegister() {
//		
//		BoardVO board = new BoardVO();
//		board.setTitle("새로 작성하는 글");
//		board.setContent("새로 작성하는 내용");
//		board.setWriter("newbie");
//		
//		service.register(board);
//		
//		System.out.println("생성된 게시물의 번호  : " + board.getBno());
//		
//	}
	
	
//	목록 작업의 구현과 테스트 코드 
	@Test
	public void tesatGetList() {
//		service.getList().forEach(board -> System.out.println(board));
		service.getListWithPaging(new Criteria(2, 10)).forEach(board -> System.out.println(board));
	}
	
//	조회 작업의 구현과 테스트코드!
//	@Test
//	public void testGet() {
//		System.out.println(service.get(1L));
//	}
	
	
	
//	수정 작업의 구현과 테스트코드
//	@Test
//	public void testUpdate() {
//		
//		BoardVO board = service.get(1L);
//		
//		if(board == null) {
//			return;
//		}
//		
//		board.setTitle("제목 수정합니다.");
//		System.out.println("Modify Result : " + service.modify(board));
//		
//	}
	
	
//	삭제 작업의 구현과 테스트 코드
//	@Test
//	public void testDelete() {
////		게시물 번호의 존재 여부를 확인하고 테스트 할 것
//		System.out.println("Remove Result : "+service.remove(2L));
//	}
	
	
	

}





















