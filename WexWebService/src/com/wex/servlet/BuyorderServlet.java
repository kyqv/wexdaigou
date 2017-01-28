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

import com.wex.model.Buyorder;
import com.wex.model.Customer;
import com.wex.model.OrderShip;
import com.wex.model.Result;
 
import com.wex.service.BuyorderService;
import com.wex.service.CustomerService;
import com.wex.util.JsonUtil;
public class BuyorderServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;

	BuyorderService buyorderService;
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
		actionList.add("getShip");
		actionList.add("del");
		actionList.add("edit");
		actionList.add("ship");				

		try{
			this.ValidateLogin(request, response);
			this.ValidateAction(request, response);
		}catch(Exception e){
			return;
		}
		
		//bussiness process
		try {
			buyorderService = new BuyorderService();
			customerService = new CustomerService();
			this.ListAction(request, response);
			this.GetAction(request, response);
			this.GetShipAction(request, response);
			this.DelAction(request, response);
			this.EditAction(request, response);
			this.ShipAction(request, response);
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
		
		String orderId = request.getParameter("orderId");
		Buyorder buyorder = buyorderService.getBuyorderById(orderId);
					
		String data = JsonUtil.Entity2Json(buyorder);
 
		result = new Result();
		result.setData(data);
		result.setSuccessful(true);
		return true;
	}
	
	protected boolean GetShipAction(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		if(!"getShip".equals(action)){
			return false;
		}
		
		String orderId = request.getParameter("orderId");
		OrderShip orderShip = buyorderService.getOrderShip(orderId);
					
		String data = JsonUtil.Entity2Json(orderShip);
 
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
		
		String orderId = request.getParameter("orderId");
		buyorderService.DelBuyorder(orderId);
 				
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
 
		Buyorder buyorder = (Buyorder)JsonUtil.Json2Entity(jsonData, Buyorder.class);
		String orderId = buyorder.getOrderId();
 
		
		if(buyorderService.CheckBuyorderExist(orderId)) {
			buyorderService.UpdateBuyorder(buyorder);
		} else {
			buyorderService.AddBuyorder(buyorder);
		}
		
		Customer customer = new Customer();
		customer.setAddress(buyorder.getAddress());
		customer.setMobile(buyorder.getMobile());
		customer.setName(buyorder.getName());
		customer.setWeixin(buyorder.getWeixin());
		
		String mobile = customer.getMobile();
		if(customerService.CheckCustomerExistByMobile(mobile)){
			customerService.UpdateCustomerByMobile(customer);
		}else{
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
		
		String buyId = request.getParameter("buyId");
		String start = request.getParameter("start");
		String cnt = request.getParameter("cnt");
		String searchTxt = request.getParameter("searchTxt");
		
		List<Object> objList = new ArrayList<Object>(); 
		objList.addAll(buyorderService.getBuyorderList(buyId,start, cnt, searchTxt));
		String data = JsonUtil.List2Json(objList);
		
		result = new Result();
		result.setData(data);
		result.setSuccessful(true);
		return true;
	}	
	
 
	protected boolean ShipAction(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		if(!"ship".equals(action)){
			return false;
		}
		
		String jsonData = request.getParameter("jsonData");
		//System.out.println("jsonData:"+jsonData);
		Buyorder buyorder = (Buyorder)JsonUtil.Json2Entity(jsonData, Buyorder.class);
		String orderId = buyorder.getOrderId();
		//System.out.println("orderId:"+orderId);
		
		if(!buyorderService.CheckBuyorderExist(orderId)) {
			result = new Result();
			result.setSuccessful(false);
			result.setErrCode("ERR_NOT_FOUND");
			result.setErrMsg("¶©µ¥²»´æÔÚ");
			return false;
		}
		
		buyorderService.UpdateStatus(buyorder);
		
		
		OrderShip orderShip = (OrderShip)JsonUtil.Json2Entity(jsonData, OrderShip.class);
		
		buyorderService.UpdateOrderShip(orderShip);
				
		result = new Result();
	 	result.setSuccessful(true);
		return true;
	}
	
}
