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
 
import com.wex.service.CommService;
 

public class CommServlet extends BaseServlet{
	private static final long serialVersionUID = 1L;
	
	CommService commService;
	 

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		 
		actionList.add("getRmbEx");

		try{
			this.ValidateLogin(request, response);
			this.ValidateAction(request, response);
		}catch(Exception e){
			return;
		}
		
		//bussiness process
		try {
			commService = new CommService();
			this.getRmbExAction(request, response);
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
	
	protected boolean getRmbExAction(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		if(!"getRmbEx".equals(action)){
			return false;
		}
		
		String data = commService.getRmbEx();
				
		result = new Result();
		result.setData(data);
		result.setSuccessful(true);
		return true;
	}

	 
	
	 
}
