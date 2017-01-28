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

import com.wex.model.Product;
import com.wex.model.ProductImg;
import com.wex.model.Result;
import com.wex.service.ProductService;
import com.wex.util.JsonUtil;
import com.wex.util.ObjUtil;

public class ProductServlet extends BaseServlet{
	
	private static final long serialVersionUID = 1L;
	
	ProductService productService;
	 

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
		actionList.add("getImg");

		try{
			this.ValidateLogin(request, response);
			this.ValidateAction(request, response);
		}catch(Exception e){
			return;
		}
		
		//bussiness process
		try {
			productService = new ProductService();
			this.ListAction(request, response);
			this.GetAction(request, response);
			this.GetImgAction(request, response);
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
 		
		String prodId = request.getParameter("prodId");
		Product product = productService.getProductById(prodId);
					
		String data = JsonUtil.Entity2Json(product);
 
		result = new Result();
		result.setData(data);
		result.setSuccessful(true);
		return true;
	}
	
 
	protected boolean GetImgAction(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		if(!"getImg".equals(action)){
			return false;
		}
 		
		String prodId = request.getParameter("prodId");
		ProductImg productImg = productService.getProductImg(prodId);
					
		String data = JsonUtil.Entity2Json(productImg);
 
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
		
		String prodId = request.getParameter("prodId");
		productService.DelProduct(prodId);
  				
		result = new Result();
		result.setSuccessful(true);
		return true;
	}
	
	@Override
	protected boolean EditAction(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		if(!super.EditAction(request, response)){
			return false;
		}
		
		String imgData = request.getParameter("imgData");
		String jsonData = request.getParameter("jsonData");
		//System.out.println("jsonData:"+jsonData);
		Product product = (Product)JsonUtil.Json2Entity(jsonData, Product.class);
		String prodId = product.getProdId();
		//System.out.println("shop:"+product.getShop());
		
		if(productService.CheckProductExist(prodId)) {
			productService.UpdateProduct(product);
			if(!ObjUtil.isEmpty(imgData)){
				ProductImg productImg = new ProductImg();
				productImg.setProdId(prodId);
				productImg.setImg(imgData);
				productService.UpdateProductImg(productImg);
			}
			
		} else {
			productService.AddProduct(product);
			prodId = productService.getLastInsertId();
			if(!ObjUtil.isEmpty(imgData) && productService.CheckProductExist(prodId)){
				ProductImg productImg = new ProductImg();
				productImg.setProdId(prodId);
				productImg.setImg(imgData);
				productService.UpdateProductImg(productImg);
			}
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
		objList.addAll(productService.getProductList(start, cnt, searchTxt));
		String data = JsonUtil.List2Json(objList);
		
		result = new Result();
		result.setData(data);
		result.setSuccessful(true);
		return true;
	}	

}
