/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */
package com.wex.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import com.wex.model.Result;
  
import com.wex.util.JsonUtil;
 
import com.wex.util.ValidateUtil;

public class BaseServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	protected List<String> actionList = new ArrayList<String>();
	protected String action;
	protected Result result;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void ReturnJson(HttpServletResponse response, Result result){
		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
		
			out.print(JsonUtil.Entity2Json(result));
			out.flush();
			out.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	protected void ReturnJson(HttpServletResponse response){
		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
		
			out.print(JsonUtil.Entity2Json(result));
			out.flush();
			out.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	protected void ValidateLogin(HttpServletRequest request, HttpServletResponse response) throws Exception{
		result = ValidateUtil.CheckLogin(request);
		if(!result.isSuccessful()){
			ReturnJson(response, result);
			throw new Exception("Check login error"); 
		}
		return;
	}
	
	protected void ValidateAction(HttpServletRequest request, HttpServletResponse response) throws Exception{

		//validate action
		action = request.getParameter("action");
		//System.out.println("base action="+action);
		result = ValidateUtil.NotEmpty(action);
		if(!result.isSuccessful()){
			ReturnJson(response, result);
			throw new Exception("Check action error"); 
		}
		
		result = ValidateUtil.CheckAction(action, actionList);
		if(!result.isSuccessful()){
			ReturnJson(response, result);
			throw new Exception("Check action error"); 
		} 
		
		return;
	}
 	
	protected boolean GetAction(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		if(!"get".equals(action)){
			return false;
		}
		return true;
	}
	
	protected boolean DelAction(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		if(!"del".equals(action)){
			return false;
		}
		return true;
	}	
	
	protected boolean EditAction(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		if(!"edit".equals(action)){
			return false;
		}
		return true;
	}		
	
	protected boolean ListAction(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		if(!"list".equals(action)){
			return false;
		}
		return true;
		
	}	
	
	protected boolean ListallAction(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		if(!"listall".equals(action)){
			return false;
		}
		return true;
		
	}	
	
	protected boolean LoginAction(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		if(!"login".equals(action)){
			return false;
		}
		return true;
		
	}	
	
	protected boolean LogoutAction(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		if(!"logout".equals(action)){
			return false;
		}
		return true;
		
	}
	
	protected boolean ChkLoginAction(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		if(!"chklogin".equals(action)){
			return false;
		}
		return true;
		
	}	
	
 
}
