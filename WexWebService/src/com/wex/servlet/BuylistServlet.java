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
import com.wex.model.Buylist;
import com.wex.model.Result;
  
import com.wex.service.BuylistService;
import com.wex.util.JsonUtil;
 
import com.wex.util.ValidateUtil;

public class BuylistServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;

	BuylistService buylistService;

	
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
			buylistService = new BuylistService();
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
		
		String buyId = request.getParameter("buyId");
		result = ValidateUtil.isInt(buyId);
		if(!result.isSuccessful()){
			return false;
		}
		
		Buylist buylist = buylistService.getBuylistById(buyId);
					
		String data = JsonUtil.Entity2Json(buylist);
		
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
		
		String buyId = request.getParameter("buyId");
		result = ValidateUtil.isInt(buyId);
		if(!result.isSuccessful()){
			return false;
		}
		
		buylistService.DelBuylist(buyId);
				
		result = new Result();
		result.setSuccessful(true);
		return true;
	}	

	@Override
	protected boolean EditAction(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		if(!super.EditAction(request, response)){
			return false;
		}
					
		String buyId = request.getParameter("buyId");
		String buyDate = request.getParameter("buyDate");
		String status = request.getParameter("status");
		
		result = ValidateUtil.isInt(buyId);
		if(!result.isSuccessful()){
			return false;
		}
		
		result = ValidateUtil.isYYYYMMDD(buyDate);
		if(!result.isSuccessful()){
			return false;
		}
 
		Buylist buylist = new Buylist();
		buylist.setBuyId(buyId);
		buylist.setDate(buyDate);
		buylist.setMonth(buyDate.substring(0, 7));
		buylist.setYear(buyDate.substring(0, 4));
		buylist.setSellPrice("0");
		buylist.setBuyPrice("0");
		buylist.setCnt("0");
		buylist.setStatus(status);
		
		if(buylistService.CheckBuylistExist(buyId)) {
			Buylist buylist1 = buylistService.getBuylistById(buyId);
			buylist.setSellPrice(buylist1.getSellPrice());
			buylist.setBuyPrice(buylist1.getBuyPrice());
			buylist.setCnt(buylist1.getCnt());
			buylistService.UpdateBuylist(buylist);
		} else {
			buylistService.AddBuylist(buylist);
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
		
		result = ValidateUtil.isInt(start);
		if(!result.isSuccessful()){
			return false;
		}
		
		result = ValidateUtil.isInt(cnt);
		if(!result.isSuccessful()){
			return false;
		}
		
		List<Object> objList = new ArrayList<Object>(); 
		objList.addAll(buylistService.getBuylistList(start, cnt, searchTxt));
		String data = JsonUtil.List2Json(objList);
		
		result = new Result();
		result.setData(data);
		result.setSuccessful(true);
		return true;
	}	
}
