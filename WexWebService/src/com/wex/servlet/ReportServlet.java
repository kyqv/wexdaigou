/*
 * $filename: JsonAjaxServlet.java,v $
 * $Date: Sep 1, 2013  $
 * Copyright (C) ZhengHaibo, Inc. All rights reserved.
 * This software is Made by Zhenghaibo.
 */
package com.wex.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wex.model.Result;
 
import com.wex.service.ReportService;
import com.wex.util.JsonUtil;

public class ReportServlet extends BaseServlet{
	private static final long serialVersionUID = 1L;
	
	ReportService reportService;
	 

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
		 
		actionList.add("topCategory");
		actionList.add("topYear");
		actionList.add("topMonth");
		actionList.add("topCustomer");			
		actionList.add("topProduct");	
		
		try{
			this.ValidateLogin(request, response);
			this.ValidateAction(request, response);
		}catch(Exception e){
			return;
		}
		
		//bussiness process
		try {
			reportService = new ReportService();
			this.TopCategoryAction(request, response);
			this.TopCustomerAction(request, response);
			this.TopMonthAction(request, response);
			this.TopProductAction(request, response);
			this.TopYearAction(request, response);
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
	
	protected boolean TopCategoryAction(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		if(!"topCategory".equals(action)){
			return false;
		}
		
		String orderBy = request.getParameter("orderBy");
		//String category = request.getParameter("category");
				
		List<Object> objList = new ArrayList<Object>(); 
		objList.addAll(reportService.getTopCategory(orderBy));
		String data = JsonUtil.List2Json(objList);
				
		result = new Result();
		result.setData(data);
		result.setSuccessful(true);
		return true;
	}

	protected boolean TopYearAction(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		if(!"topYear".equals(action)){
			return false;
		}
		
		String orderBy = request.getParameter("orderBy");
		String category = request.getParameter("category");
				
		List<Object> objList = new ArrayList<Object>(); 
		objList.addAll(reportService.getTopYear(category, orderBy));
		String data = JsonUtil.List2Json(objList);
				
		result = new Result();
		result.setData(data);
		result.setSuccessful(true);
		return true;
	}	
	
	protected boolean TopMonthAction(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		if(!"topMonth".equals(action)){
			return false;
		}
		
		String orderBy = request.getParameter("orderBy");
		String category = request.getParameter("category");
				
		List<Object> objList = new ArrayList<Object>(); 
		objList.addAll(reportService.getTopMonth(category, orderBy));
		String data = JsonUtil.List2Json(objList);
		
 		result = new Result();
		result.setData(data);
		result.setSuccessful(true);
		return true;
	}	
	
	protected boolean TopProductAction(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		if(!"topProduct".equals(action)){
			return false;
		}
		
		String orderBy = request.getParameter("orderBy");
		String category = request.getParameter("category");
				
		List<Object> objList = new ArrayList<Object>(); 
		objList.addAll(reportService.getTopProduct(category, orderBy));
		String data = JsonUtil.List2Json(objList);
		
 		result = new Result();
		result.setData(data);
		result.setSuccessful(true);
		return true;
	}
	
	protected boolean TopCustomerAction(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		if(!"topCustomer".equals(action)){
			return false;
		}
		
		String orderBy = request.getParameter("orderBy");
		String category = request.getParameter("category");
				
		List<Object> objList = new ArrayList<Object>(); 
		objList.addAll(reportService.getTopCustomer(category, orderBy));
		String data = JsonUtil.List2Json(objList);
		
 		result = new Result();
		result.setData(data);
		result.setSuccessful(true);
		return true;
	}
	
	 
}
