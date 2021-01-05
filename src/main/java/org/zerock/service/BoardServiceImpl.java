package org.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.mapper.BoardMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {

	// Spring 4.3 이상에서 자동 처리
	@Setter(onMethod_ = @Autowired)
	private BoardMapper mapper;

	
	@Override
	public List<BoardVO> getList() {
		System.out.println("get List with Criteria : " +mapper.getList());
		return mapper.getList();
	}

	@Override
	public List<BoardVO> getListWithPaging(Criteria cri) {
		System.out.println("get List with Criteria : " +mapper.getListWithPaging(cri));
		return mapper.getListWithPaging(cri);
	}

	
	
//	등록 작업의 구현과 테스트!!
	@Override
	public void register(BoardVO board) {
		
		System.out.println("register  @@@@@@@@" +board);
		
		mapper.insertSelectKey(board);
		
	}

	
//	조회 작업의 구현과 테스트
	@Override
	public BoardVO get(Long bno) {
		
		System.out.println("get........"+bno);
		return mapper.read(bno);
	}

	
//	수정 구현과 테스트
	@Override
	public boolean modify(BoardVO board) {
		System.out.println("modify......."+board);
		return mapper.update(board)==1;   // 수정이 정상적으로 이루어 지면 1이라는 값이 반환이 되기떄문에 == 연산자를 사용해야 한다.
	}

//	삭제 구현과 테스트
	@Override
	public boolean remove(Long bno) {
		System.out.println("delete................"+bno);
		return mapper.delete(bno) == 1;  // 삭제가 정상적으로 이루어지면 1이라는 값이 반환이 되기 때문에  == 연산자를 사용해야 한다.
 	}

	
	@Override
	public int getTotalCount(Criteria cri) {
		// TODO Auto-generated method stub
		return mapper.getTotalCount(cri);
	}


	





}




















