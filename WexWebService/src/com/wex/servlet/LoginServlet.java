/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */
package com.wex.servlet;

import java.io.IOException;
 
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.wex.model.Result;
import com.wex.model.User;
import com.wex.service.UserService;
import com.wex.util.ObjUtil;
import com.wex.util.ValidateUtil;

public class LoginServlet extends BaseServlet{
 
	private static final long serialVersionUID = 1L;

	UserService userService;
 
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		/*
		System.out.println("header start");
		Enumeration headers = request.getHeaderNames();
		while(headers.hasMoreElements()){
			String key = (String)headers.nextElement();
			String value = request.getHeader(key);
			System.out.println(key + "="+value);
		}
		System.out.println("header end");
		*/
		
		request.setCharacterEncoding("utf-8");
		
		actionList.add("login");
		actionList.add("logout");
		actionList.add("chklogin");
		
		try{
			this.ValidateAction(request, response);
		}catch(Exception e){
			return;
		}
		
		//bussiness process
		try {
			userService = new UserService();
			this.LoginAction(request, response);
			this.LogoutAction(request, response);
			this.ChkLoginAction(request, response);
		}catch(SQLException e){
			//e.printStackTrace();
			result = new Result();
			result.setSuccessful(false);
			result.setErrCode("ERR_DB_ERROR");
			result.setErrMsg(e.getMessage());
			ReturnJson(response, result);
			return;
		}
		
		ReturnJson(response, result);
		return;
 
	}
	
	@Override
	protected boolean LoginAction(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		
		if(!super.LoginAction(request, response)){
			return false;
		}
		
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		
		result = ValidateUtil.NotEmpty(userId);
		if(!result.isSuccessful()){
			return false;
		}
		
		/*
		result = ValidateUtil.MaxLength(userId,15);
		if(!result.isSuccessful()){
			return false;
		}
		result = ValidateUtil.MinLength(userId,6);
		if(!result.isSuccessful()){
			return false;
		}
		*/
		
		result = ValidateUtil.NotEmpty(password);
		if(!result.isSuccessful()){
			return false;
		}
		/*
		result = ValidateUtil.MaxLength(password,100);
		if(!result.isSuccessful()){
			return false;
		}
		result = ValidateUtil.MinLength(password,6);
		if(!result.isSuccessful()){
			return false;
		}
		*/
		if(!ValidateUtil.protectLogin(userId, request.getSession())){
			result = new Result();
			result.setSuccessful(false);
			result.setErrCode("ERR_INVALID_RETRY");
			result.setErrMsg("密码输入错误次数过多,帐号被锁定,请联系系统管理员!");
			return false;
		}
		
		if(!userService.CheckUserExist(userId)){
			result = new Result();
			result.setSuccessful(false);
			result.setErrCode("ERR_INVALID_USER");
			result.setErrMsg("用户名不存在");
			return false;
		}
		
		User user = userService.CheckUserPassword(userId, password);
		
		result = new Result();
		
		if(ObjUtil.isEmpty(user)){
			result = new Result();
			result.setSuccessful(false);
			result.setErrCode("ERR_INVALID_PASS");
			result.setErrMsg("用户密码错误");
		} else {
			result.setSuccessful(true);
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
		}
 
		return true;
	}	
	
	@Override
	protected boolean LogoutAction(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		if(!super.LogoutAction(request, response)){
			return false;
		}
		
		result = new Result();
		
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		
		result.setSuccessful(true);
		return true;
	}	
	
	@Override
	protected boolean ChkLoginAction(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		if(!super.ChkLoginAction(request, response)){
			return false;
		}
		
		//检查是否已登录
		result = ValidateUtil.CheckLogin(request);
		if(!result.isSuccessful()){
			ReturnJson(response, result);
			return false;
		}
 		
		result = new Result();
		result.setSuccessful(true);
		return true;
	}		
}
