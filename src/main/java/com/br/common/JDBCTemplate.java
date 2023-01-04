package com.br.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {
	
	public static void main(String[] args) {
		getConnection();
	} // 한 번 실행시켜보기
	
	public static Connection getConnection() {
		
		Connection conn = null;
		Properties prop = new Properties();
		
		// 읽어들이고자하는파일 : driver.properties (단, src/main/java 안에 있는 파일 말고, webapp/WEB-INF/classes 폴더 내에 있는 파일)
		// src/main/webapp/WEB-INF/classes/db/driver/driver.properties 파일의 물리적인 경로
		String filePath = JDBCTemplate.class.getResource("/db/driver/driver.properties").getPath();
		// "C:/workspaces/06_web-workspace/webProject/src/main/webapp/WEB-INF/classes/~~~"
		
		try {
			prop.load(new FileInputStream(filePath));
			System.out.println("파일 읽어들이기 완료");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			// jdbc driver 등록
			Class.forName(prop.getProperty("driver")); // ojdbc.jar 파일 WEB-INF/lib
			// db의 url, 계정명, 비밀번호 제시해서 Connection 생성
			conn = DriverManager.getConnection(prop.getProperty("url"),
					 						   prop.getProperty("username"),
					 						   prop.getProperty("password"));
			
			conn.setAutoCommit(false);
			// mac을 쓰는 사람도 있어서 필요한 구문
			
			System.out.println("Connection 객체 생성 완료");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
		
	}
	
	public static void commit(Connection conn) {
		
		try {
			if(conn != null && !conn.isClosed()) {
				conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public static void rollback(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Statement stmt) {
		try {
			if(stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rset) {
		try {
			if(rset != null && !rset.isClosed()) {
				rset.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
