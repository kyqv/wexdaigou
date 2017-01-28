/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */
package com.wex.service;

import java.sql.SQLException;
import com.wex.dao.UserDao;
import com.wex.model.User;

public class UserService {
	UserDao userDao = new UserDao();
	
	public UserService() throws SQLException{
		userDao = new UserDao();
	}
	
	public boolean CheckUserExist(String userId) throws SQLException{
		return userDao.CheckUserExist(userId);
	}
 
	public User getUserById(String userId) throws SQLException{
		if(userDao.CheckUserExist(userId)) {
			return userDao.getUserById(userId);
		}else{
			return new User();
		}
	}
	
	public int AddUser(User user) throws SQLException{
		return userDao.AddUser(user);
	}
	
	public int DelUser(String userId) throws SQLException{
		return userDao.DelUser(userId);
	}	
	
	public int UpdateUser(User user) throws SQLException{
		return userDao.UpdateUser(user);
	}	
	
	public User CheckUserPassword(String userId, String password) throws SQLException{
		if(userDao.CheckUserExist(userId)) {
			User user = userDao.getUserById(userId);
			if(password.equals(user.getPassword())){
				return user;
			} else{
				return null;
			}	
		}else{
			return null;
		}
	}
 

}
