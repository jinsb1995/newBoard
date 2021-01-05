package org.zerock.persistence;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

import lombok.extern.log4j.Log4j;

@Log4j
public class JDBCTests {

//	main() 수행시 가장 먼저 실행
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("ojdbc8.jar => ojdbc6_g.jar로 실행해 주세요");
		}
	}
	
	@Test
	public void testConnection() {
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		try(Connection conn = DriverManager.getConnection(url, "spring", "spring")) {
			System.out.println(conn);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	
	
}
