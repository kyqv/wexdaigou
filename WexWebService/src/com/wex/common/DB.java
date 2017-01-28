/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */
package com.wex.common;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {
	private static String driver = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost/wex";
	private static String user = "root";
	private static String pwd = "123456";
	
	private static Connection conn = null;
	private static Statement stmt = null;
	private static PreparedStatement pstmt = null;
	private static ResultSet rs = null;
	
	private static String FilePath = "/wex.properties";
	private static Properties prop = new Properties();
	
	private static boolean debugMode = false;
	
	
	/*构造方法*/
	public DB() throws SQLException{
				
		try
		{
			InputStream ins = this.getClass().getResourceAsStream(FilePath);
			prop.load(ins);
			driver = prop.getProperty("driverName");
			url =  prop.getProperty("connectURL");
			user =  prop.getProperty("dbID");
			pwd =  prop.getProperty("dbPass");
		}
		catch (IOException e){
			//e.printStackTrace();
			throw new SQLException("Cannot load DB connection properties file");
		}
				
		/*
		System.out.println("driver=" + driver);
		System.out.println("url=" + url);
		System.out.println("user=" + user);
		System.out.println("pwd=" + pwd);
		*/
		conn = getConnection();
	}
	
	
	
	/*内部方法，用于获得与数据库连接*/
	private Connection getConnection() throws SQLException{
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url, user, pwd);
		} catch (InstantiationException e) {
			throw new SQLException(e.toString());
		} catch (IllegalAccessException e) {
			throw new SQLException(e.toString());
		} catch (ClassNotFoundException e) {
			throw new SQLException(e.toString());
		}  
		return conn;
	}
	
	/*内部方法，用于得到statement*/
	private Statement getStatement() throws SQLException{
 
		//stmt = getConnection().createStatement();
		stmt = conn.createStatement();
 
		return stmt;
	}
	
	/*外部方法，用于得到初始化完的preparedstatement*/
	public PreparedStatement getPreStatement(String sql,Object...o) throws SQLException{

		//pstmt = getConnection().prepareStatement(sql);
		pstmt = conn.prepareStatement(sql);
		
		for (int i = 0; i < o.length; i++) {
			pstmt.setObject(i+1, o[i]);
		}
		return pstmt;
	}
	
	/*外部方法，用于stmt的查询*/
	public ResultSet select(String sql) throws SQLException{
		if(debugMode) {
			System.out.println("sql:"+sql);
		}
		
		rs = getStatement().executeQuery(sql);

		//stmt.close();
		return rs;
	}
	
	/*外部方法，用于preparedstatement的查询*/
	public ResultSet select(String sql,Object...o) throws SQLException{
		if(debugMode) {
			System.out.println("sql:"+sql);
		}
		rs = getPreStatement(sql, o).executeQuery();
 
		//pstmt.close();
		return rs;
	}
	
	/*外部方法，用于stmt的增删改*/
	public int update(String sql) throws SQLException{
		if(debugMode) {
			System.out.println("sql:"+sql);
		}
		
		int i = 0;
		i = getStatement().executeUpdate(sql);

		//stmt.close();
		return i;
	}
	
	/*外部方法，用于preparedstatement的增删改*/
	public int update(String sql,Object...o) throws SQLException{
		if(debugMode) {
			System.out.println("sql:"+sql);
		}
		int i = 0;
		i = getPreStatement(sql, o).executeUpdate();
		
		//pstmt.close();
		return i;
	}
	
	/*外部方法，用于处理事务*/
	public int update(PreparedStatement[] pstmt) throws SQLException{
		conn.setAutoCommit(false);

		int j = 0;
		for (int i = 0; i < pstmt.length; i++) {
			j = pstmt[i].executeUpdate() + j;
			if (j == 0) {
				conn.rollback();
				return 0;
			}
		}

		conn.setAutoCommit(true);
		return j;
	}
	
	//该方法主要用来关闭自动提交
	public void closeAutoSubmit() throws SQLException{
		conn.setAutoCommit(false);
	}
	
	//该方法主要用来打开自动提交
	public void openAutoSubmit() throws SQLException{
		conn.commit();
		conn.setAutoCommit(true);
	}
	
	
	/*外部方法，用于关闭连接*/
	public void close() throws SQLException{
		if (rs != null) {
			rs.close();
		}
		if (stmt != null) {
			stmt.close();	
		}
		if (pstmt != null) {
			pstmt.close();
		}
		if (conn != null) {
			conn.close();
		}			
	}
}
