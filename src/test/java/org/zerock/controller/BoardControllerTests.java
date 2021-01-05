package org.zerock.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

// 서버(tomcat[WAS])를 실행하지 않고 controller을 테스트 할 수 있는 방법을 코딩할 것임

/*
 * @RunWith은 테스트 코드가 스프링을 실행하는 역할을 할 것이라고 표시
 * @ContextConfiguration : 두번째로 중요한 설정!! 지정된 클래스나 문자열을 이용해서 필요한 객체들을 스프링 내에 객체로 등록하게 된다. (흔히 스프링의 빈으로 등록된다고 표현한다)
 *                       : 얘는 classpath : 나   file : 을 이용하여   "root-context.xmldml 경로를 지정한다.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration    // Servlet 와 비슷한 존재
@ContextConfiguration({
	"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@Log4j
public class BoardControllerTests {
	
//	@Autowired는 스프링 내부에서 자신이 특정한 객체에 의존적이므로 자신에게 해당 타입의 빈을 주입해 주라는 표시이다.
//	
	@Setter(onMethod_ = {@Autowired})
	private WebApplicationContext ctx;
	
//	가짜로 URL과 파라미터 등을 부라우저에서 사용하는 것처럼 만들어서 Controller를 실행해 볼 수 있습니다.
	private MockMvc mockMvc;   // mockMVC는   가짜 MVC
	
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
//	MockMvcRequestBuilders를 이용해서 GET방식의 호출을 한다.
	@Test
	public void testList() throws Exception {
		System.out.println(
				mockMvc.perform(MockMvcRequestBuilders.get("/board/list"))
				.andReturn()
				.getModelAndView()
				.getModelMap());
	}
	
	@Test
	public void testListPaging() throws Exception {
		System.out.println(mockMvc.perform(
				MockMvcRequestBuilders.get("/board/list")
				.param("pageNum", "2")
				.param("amount", "30"))
				.andReturn().getModelAndView().getModelMap());
	}
	
	
	
	
//	등록 처리와 테스트     post는 param을 이용해서 값을 전달
//	@Test
//	public void testRegister() throws Exception {
//		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/register")
//				.param("title", "테스트 새글 제목")
//				.param("writer", "user00")
//				.param("content", "테스트 새글 내용")
//				).andReturn().getModelAndView().getViewName();
//		
//		System.out.println(resultPage);
//	}
	
	
//	조회 처리와 테스트 코드!!!!
//	@Test
//	public void testGet() throws Exception {
//		System.out.println(mockMvc.perform(MockMvcRequestBuilders.get("/board/get")
//				.param("bno", "1"))
//				.andReturn()
//				.getModelAndView().getModelMap());
//	}
	
//	수정 처리와 테스트 코드!!!
//	@Test
//	public void testModify() throws Exception {
//		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/modify")
//								.param("bno", "1")
//								.param("title", "수정된 테스트 새글 제목")
//								.param("content", "수정된 테스트 새글 내용")
//								.param("writer","user00"))
//							.andReturn().getModelAndView().getViewName();
//		
//		System.out.println(resultPage);
//	}
	
	
//	삭제 처리와 테스트 코드!!!
//	@Test
//	public void testRemove() throws Exception {
//		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/remove")
//												.param("bno", "66")
//											).andReturn().getModelAndView().getViewName();
//		System.out.println(resultPage);
//	}
	
	

	
	
	
	
	
	
	
}





















