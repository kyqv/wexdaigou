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
import com.wex.model.Customer;
import com.wex.model.Result;
import com.wex.service.CustomerService;
import com.wex.util.JsonUtil;

public class CustomerServlet extends BaseServlet{
	private static final long serialVersionUID = 1L;
	
	CustomerService customerService;
 

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
		
		actionList.add("list");
		actionList.add("get");
		actionList.add("del");
		actionList.add("edit");			

		try{
			this.ValidateLogin(request, response);
			this.ValidateAction(request, response);
		}catch(Exception e){
			return;
		}
		
		//bussiness process
		try {
			customerService = new CustomerService();
			this.ListAction(request, response);
			this.GetAction(request, response);
			this.DelAction(request, response);
			this.EditAction(request, response);
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
		
		String customerId = request.getParameter("customerId");
		
		Customer customer = customerService.getCustomerById(customerId);
					
		String data = JsonUtil.Entity2Json(customer);
 
		
		result = new Result();
		result.setData(data);
		result.setSuccessful(true);
		return true;
	}
	
	@Override
	protected boolean DelAction(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		if(!super.DelAction(request, response)){
			return false;
		}
		
 	
		String customerId = request.getParameter("customerId");
		customerService.DelCustomer(customerId);
 				
		result = new Result();
 		result.setSuccessful(true);
		return true;
	}
	
	@Override
	protected boolean EditAction(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		if(!super.EditAction(request, response)){
			return false;
		}
 
		
		String jsonData = request.getParameter("jsonData");
		//System.out.println("jsonData:"+jsonData);
		Customer customer = (Customer)JsonUtil.Json2Entity(jsonData, Customer.class);
		String customerId = customer.getCustomerId();
		//System.out.println("customerId:"+customerId);
		
		if(customerService.CheckCustomerExist(customerId)) {
			customerService.UpdateCustomer(customer);
		} else {
			customerService.AddCustomer(customer);
		}
				
		result = new Result();
		result.setSuccessful(true);
		return true;
	}		
	
	@Override
	protected boolean ListAction(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		if(!super.ListAction(request, response)){
			return false;
		}
		
 		String start = request.getParameter("start");
		String cnt = request.getParameter("cnt");
		String searchTxt = request.getParameter("searchTxt");
		
		List<Object> objList = new ArrayList<Object>(); 
		objList.addAll(customerService.getCustomerList(start, cnt, searchTxt));
		String data = JsonUtil.List2Json(objList);
		
		result = new Result();
		result.setData(data);
		result.setSuccessful(true);
		return true;
	}	
}
