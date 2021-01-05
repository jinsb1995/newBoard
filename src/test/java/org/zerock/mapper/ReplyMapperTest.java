package org.zerock.mapper;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ReplyMapperTest {
	
	
	private Long[] bnoArr = { 3145745L, 3145744L, 3145743L, 3145742L, 3145741L };
	
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;
	
	
//	@Test
	public void testCreate() {
		IntStream.rangeClosed(1, 10).forEach(i -> {
			ReplyVO vo = new ReplyVO();
			
			//게시물 번호
			vo.setBno(bnoArr[i % 5]);
			vo.setReply("댓글 테스트 " + i);
			vo.setReplyer("replyer" + i);
			
			mapper.insert(vo);
			
		});
	}

//	@Test
	public void testRead() {
		Long targetRno = 5L;
		
		ReplyVO vo = mapper.read(targetRno);
		
		System.out.println(vo);
	}
	
//	@Test
	public void testDelete() {
		
		Long targetRno = 1L;
		
		mapper.delete(targetRno);
	}
	
//	@Test
	public void testUpdate() {
		
		Long targetRno = 10L;
		
		ReplyVO vo = mapper.read(targetRno);
		
		vo.setReply("Update Reply ");
		
		int count = mapper.update(vo);
		
		System.out.println("UPDATE COUNT :  " + count);
		
	}

//	@Test
	public void testGetListWithPaging() {
		Criteria cri = new Criteria();
		
		// 3145745L
		List<ReplyVO> replies = mapper.getListWithPaging(cri, bnoArr[0]);
		
		replies.forEach(reply -> System.out.println(reply));
	}
	
	
	
//	@Test
	public void testMapper() {
		System.out.println(mapper);
	}
	
	
	@Test
	public void testList2() {
		Criteria cri = new Criteria(2, 10);
		
		// 4718593L
		List<ReplyVO> replies = mapper.getListWithPaging(cri, 4718593L);
		
		replies.forEach(reply -> System.out.println(reply));
	}
	
	
	
	
	
	
	
	
}
