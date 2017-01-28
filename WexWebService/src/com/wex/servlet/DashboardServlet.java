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

import com.wex.model.Result;
import com.wex.service.DashboardService;
import com.wex.util.JsonUtil;

public class DashboardServlet extends BaseServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	DashboardService dashboardService;

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
		
		actionList.add("get");
 
		try{
			this.ValidateLogin(request, response);
			this.ValidateAction(request, response);
		}catch(Exception e){
			return;
		}
		 
		//bussiness 
		try {
			dashboardService = new DashboardService();
			this.GetAction(request, response);
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
	protected boolean GetAction(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		if(!super.GetAction(request, response)){
			return false;
		}
		
		String data = JsonUtil.Entity2Json(dashboardService.getDashboard());
		
		result = new Result();
		result.setData(data);
		result.setSuccessful(true);
		
		return true;
	}
}
