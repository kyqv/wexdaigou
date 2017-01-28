/*
 * $filename: JsonAjaxServlet.java,v $
 * $Date: Sep 1, 2013  $
 * Copyright (C) ZhengHaibo, Inc. All rights reserved.
 * This software is Made by Zhenghaibo.
 */
package com.wex.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wex.model.Result;
import com.wex.model.User;
import com.wex.service.UserService;
import com.wex.util.JsonUtil;
import com.wex.util.ObjUtil;

public class ChgPasswordServlet extends BaseServlet{
	private static final long serialVersionUID = 1L;
	
	UserService userService;
	

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		result = new Result();
		request.setCharacterEncoding("utf-8");
		
 		//String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		String newPassword = request.getParameter("newPassword");
		String dupPassword = request.getParameter("dupPassword");		
		
		/*
		System.out.println("userId:"+userId);
		System.out.println("password:"+password);
		System.out.println("newPassword:"+newPassword);
		System.out.println("dupPassword:"+dupPassword);
		*/
		
		
		if(ObjUtil.isEmpty(password)){
			result.setSuccessful(false);
			result.setErrCode("ERR_EMPTY_USER");
			result.setErrMsg("旧密码不能为空");
			ReturnJson(response, result);
			return;
		}
		
		if(ObjUtil.isEmpty(newPassword) || ObjUtil.isEmpty(dupPassword)){
			result.setSuccessful(false);
			result.setErrCode("ERR_EMPTY_PASSWORD");
			result.setErrMsg("新密码不能为空");
			ReturnJson(response, result);
			return;
		}
		
		if(!newPassword.equals(dupPassword)){
			result.setSuccessful(false);
			result.setErrCode("ERR_EMPTY_PASSWORD");
			result.setErrMsg("重复密码不正确");
			ReturnJson(response, result);
			return;
		}
		
		//password=URLDecoder.decode(password, "utf-8");
		//userId=URLDecoder.decode(userId, "utf-8");

		//System.out.println("password:"+password);
		User user = (User)request.getSession().getAttribute("user");
		if(ObjUtil.isEmpty(user)){
			result.setSuccessful(false);
			result.setErrCode("ERR_INV_SESSION");
			result.setErrMsg("请先登录");
			ReturnJson(response, result);
			return;
		}
		String userId = user.getUserId();
		
		//bussiness process
		try {
			userService = new UserService();
			user = userService.CheckUserPassword(userId, password);
			if(ObjUtil.isEmpty(user)){
				result.setSuccessful(false);
				result.setErrCode("ERR_INVALID_USER");
				result.setErrMsg("旧密码不正确");
			} else {
				user.setPassword(dupPassword);
				if(userService.UpdateUser(user) > 0) {
					result.setSuccessful(true);
					result.setData(JsonUtil.Entity2Json(user));
				} else {
					result.setSuccessful(false);
					result.setErrCode("ERR_DB_ERROR");
					result.setErrMsg("系统错误");
				}
			}
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
	
 
}
