/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */
package com.wex.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.wex.common.DB;
import com.wex.model.User;

public class UserDao {
	DB db;
	
	/*构造方法*/
	public UserDao() throws SQLException{
		db = new DB();
	}
	
	public boolean CheckUserExist(String userId) throws SQLException{
		String sql = "select count(*) as rowCnt from user where userId=?;";

 		ResultSet rs = db.select(sql,userId);
		rs.next();
		int rowCnt = rs.getInt("rowCnt");
		if(rowCnt > 0){
			return true;
		} else {
			return false;
		}
 
	}
	
 	public User getUserById(String userId) throws SQLException{
		String sql = "select * from user where userId=?";
		User user = new User();

		ResultSet rs = db.select(sql,userId);
		rs.next();
		user.setUserId(rs.getString("userId"));
		user.setPassword(rs.getString("password"));

		return user;
	}
	
	public int AddUser(User user) throws SQLException{
		String sql = "insert into user(userId,password) values(?,?);";
		return db.update(sql,user.getUserId(),user.getPassword());
	}
	
	public int DelUser(String userId) throws SQLException{
		String sql = "delete from user where userId=?;";
		return db.update(sql,userId);
	}	
	
	public int UpdateUser(User user) throws SQLException{
		String sql = "update user set password=? where userId=?;";
		return db.update(sql,user.getPassword(),user.getUserId());
	}	

}
