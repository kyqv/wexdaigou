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
	
	
	/*���췽��*/
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
	
	
	
	/*�ڲ����������ڻ�������ݿ�����*/
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
	
	/*�ڲ����������ڵõ�statement*/
	private Statement getStatement() throws SQLException{
 
		//stmt = getConnection().createStatement();
		stmt = conn.createStatement();
 
		return stmt;
	}
	
	/*�ⲿ���������ڵõ���ʼ�����preparedstatement*/
	public PreparedStatement getPreStatement(String sql,Object...o) throws SQLException{

		//pstmt = getConnection().prepareStatement(sql);
		pstmt = conn.prepareStatement(sql);
		
		for (int i = 0; i < o.length; i++) {
			pstmt.setObject(i+1, o[i]);
		}
		return pstmt;
	}
	
	/*�ⲿ����������stmt�Ĳ�ѯ*/
	public ResultSet select(String sql) throws SQLException{
		if(debugMode) {
			System.out.println("sql:"+sql);
		}
		
		rs = getStatement().executeQuery(sql);

		//stmt.close();
		return rs;
	}
	
	/*�ⲿ����������preparedstatement�Ĳ�ѯ*/
	public ResultSet select(String sql,Object...o) throws SQLException{
		if(debugMode) {
			System.out.println("sql:"+sql);
		}
		rs = getPreStatement(sql, o).executeQuery();
 
		//pstmt.close();
		return rs;
	}
	
	/*�ⲿ����������stmt����ɾ��*/
	public int update(String sql) throws SQLException{
		if(debugMode) {
			System.out.println("sql:"+sql);
		}
		
		int i = 0;
		i = getStatement().executeUpdate(sql);

		//stmt.close();
		return i;
	}
	
	/*�ⲿ����������preparedstatement����ɾ��*/
	public int update(String sql,Object...o) throws SQLException{
		if(debugMode) {
			System.out.println("sql:"+sql);
		}
		int i = 0;
		i = getPreStatement(sql, o).executeUpdate();
		
		//pstmt.close();
		return i;
	}
	
	/*�ⲿ���������ڴ�������*/
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
	
	//�÷�����Ҫ�����ر��Զ��ύ
	public void closeAutoSubmit() throws SQLException{
		conn.setAutoCommit(false);
	}
	
	//�÷�����Ҫ�������Զ��ύ
	public void openAutoSubmit() throws SQLException{
		conn.commit();
		conn.setAutoCommit(true);
	}
	
	
	/*�ⲿ���������ڹر�����*/
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
