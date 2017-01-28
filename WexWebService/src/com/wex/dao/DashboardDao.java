/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */
package com.wex.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
 

import com.wex.common.DB;
 


public class DashboardDao {
	DB db;
	
	/*构造方法*/
	public DashboardDao() throws SQLException{
		db = new DB();
	}
	
	public String getBuylistCnt() throws SQLException{
		String sql = "select count(*) as cnt from buylist;";
 		String cnt = "";
		ResultSet rs = db.select(sql);
		rs.next();
		cnt = rs.getString("cnt");
		return cnt;
	}
	
	public String getBuyOrderCnt() throws SQLException{
		String sql = "select count(*) as cnt from buyorder;";
 		String cnt = "";
		ResultSet rs = db.select(sql);
		rs.next();
		cnt = rs.getString("cnt");
		return cnt;
	}
	
	public String getCustomerCnt() throws SQLException{
		String sql = "select count(*) as cnt from customer;";
 		String cnt = "";
		ResultSet rs = db.select(sql);
		rs.next();
		cnt = rs.getString("cnt");
		return cnt;
	}	
	
	public String getProductCnt() throws SQLException{
		String sql = "select count(*) as cnt from product;";
 		String cnt = "";
		ResultSet rs = db.select(sql);
		rs.next();
		cnt = rs.getString("cnt");
		return cnt;
	}			
}
