package org.zerock.persistence;

import static org.junit.Assert.fail;

import java.sql.Connection;

//import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
/*
 *          이 클래스는 빈으로 등록된 DataSource를 이용해서 Connection을 제대로 처리할 수 있는지를 확인해 보는 용도입니다.
 *          testConnection을 실행해보면 내부적으로 HikariCP가 시작되고, 종료되는 로그를 확인할 수 있습니다.
 *          실행 결과는 서버 설정에 문제가 없는지를 확인만 하는 수준이다. 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class DataSourceTests {
	
	// P.92
	@Setter(onMethod_= {@Autowired})
	private SqlSessionFactory sqlSessionFactory;
	
	@Test
	public void testMyBatis() {
		try(SqlSession session = sqlSessionFactory.openSession();
			Connection conn = session.getConnection();) {
			
			System.out.println(session);
			System.out.println(conn);
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
//	@Setter(onMethod_= {@Autowired})
//	private DataSource dataSource;
	
//	@Test
//	public void testConnection() {
//		try(Connection conn = dataSource.getConnection()) {
//			System.out.println(conn);
//		} catch (Exception e) {
//			fail(e.getMessage());
//		}
//		
//		
//	}
	
}
