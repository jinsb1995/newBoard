package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
/*
 * 	     이 클래스는 BoardMapper.xml과 연동된다.
 * 	Mapper은 SQL과 그에 대한 처리를 지정하는 역할을 한다.
 */
// root-context.xml에서 org.zerock.mapper을 조사하도록 설정하고나서
// 리스트(select)와 등록(insert) 작업을 우선해서 작성합니다.
public interface BoardMapper {
	
	//밑의 Select는 MyBatis의 어노테이션을 이용해서 SQL을 메서드에 추가하는 방법이지만 불편하다
//	@Select("select * from tbl_board where bno > 0")
	public List<BoardVO> getList();
	
	public List<BoardVO> getListWithPaging(Criteria cri);
	
	public void insert(BoardVO board);
	
	public Integer insertSelectKey(BoardVO board);
	
	public BoardVO read(long bno);
	
	public int delete(long bno);
	
	public int update(BoardVO board);
	
	// Mybatis에서 전체 데이터의 개수 처리
	public int getTotalCount(Criteria cri);
	
	public void updateReplyCnt(@Param("bno") Long bno, @Param("amount") int amount);
	
	
}
