package org.zerock.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.zerock.domain.Ticket;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class SampleControllerTest {
	
//	@Setter(onMethod_ = { @AutoWired })
//	private WebApplicationContext ctx;
	
//	private MockMvc mockmvc;
	
//	@Before
//	public void setup() {
//		this.mockmvc = MockMvcBuilders.webAppContextSetup(ctx).build();
//	}
	
	
	@Test
	public void testConvert() throws Exception{
		
		Ticket ticket = new Ticket();
		ticket.setTno(123);
		ticket.setOwner("Admin");
		ticket.setGrade("AAA");
		
//		String jsonStr = new Gson().toJson(ticket);
		
		
	}

}
