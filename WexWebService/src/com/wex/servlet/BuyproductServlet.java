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
import com.wex.model.Buyproduct;
import com.wex.model.Product;
import com.wex.model.Result;
import com.wex.service.BuyproductService;
import com.wex.service.ProductService;
import com.wex.util.JsonUtil;

public class BuyproductServlet extends BaseServlet{
 
	private static final long serialVersionUID = 1L;
	
	BuyproductService buyproductService; 
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
		actionList.add("buy");
		actionList.add("listall");
		
		try{
			this.ValidateLogin(request, response);
			this.ValidateAction(request, response);
		}catch(Exception e){
			return;
		}
		
 		//bussiness process
		try {
			buyproductService = new BuyproductService();
			productService = new ProductService();
			this.ListAction(request, response);
			this.GetAction(request, response);
			this.DelAction(request, response);
			this.EditAction(request, response);
			this.BuyAction(request, response);
			this.ListallAction(request, response);
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
		
		String buyProdId = request.getParameter("buyProdId");
		Buyproduct buyproduct = buyproductService.getBuyproductById(buyProdId);
					
		String data = JsonUtil.Entity2Json(buyproduct);
 
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
		
		String buyProdId = request.getParameter("buyProdId");
		buyproductService.DelBuyproduct(buyProdId);
 				
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
		Buyproduct buyproduct = (Buyproduct)JsonUtil.Json2Entity(jsonData, Buyproduct.class);
		String buyProdId = buyproduct.getBuyProdId();
		//System.out.println("buyProdId:"+buyProdId);
		
		if(buyproductService.CheckBuyproductExist(buyProdId)) {
			buyproductService.UpdateBuyproduct(buyproduct);
		} else {
			buyproductService.AddBuyproduct(buyproduct);
		}
		
		Product product = new Product();
		product.setBuyPrice(buyproduct.getBuyPrice());
		product.setCategory(buyproduct.getCategory());
		product.setProdName(buyproduct.getProdName());
		product.setSellPrice(buyproduct.getSellPrice());
		product.setShop(buyproduct.getShop());
			
		String prodName = product.getProdName();
		if(productService.CheckProductExistByName(prodName)){
			productService.UpdateProductByName(product);
		}else{
			productService.AddProduct(product);
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
				
		String orderId = request.getParameter("orderId");
		
		List<Object> objList = new ArrayList<Object>(); 
		objList.addAll(buyproductService.getBuyproductListByOrder(orderId));
		String data = JsonUtil.List2Json(objList);
		
		result = new Result();
		result.setData(data);
		result.setSuccessful(true);
		return true;
	}	
	
	@Override
	protected boolean ListallAction(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		if(!super.ListallAction(request, response)){
			return false;
		}
		String buyId = request.getParameter("buyId");
		
		List<Object> objList = new ArrayList<Object>(); 
		objList.addAll(buyproductService.getBuyproductListByBuylist(buyId));
		String data = JsonUtil.List2Json(objList);
				
		result = new Result();
		result.setSuccessful(true);
		result.setData(data);
		return true;
	}
	
 
	protected boolean BuyAction(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		if(!"buy".equals(action)){
			return false;
		}
		
		String jsonData = request.getParameter("jsonData");
		//System.out.println("jsonData:"+jsonData);
		Buyproduct buyproduct = (Buyproduct)JsonUtil.Json2Entity(jsonData, Buyproduct.class);
		String buyProdId = buyproduct.getBuyProdId();
		//System.out.println("buyProdId:"+buyProdId);
		
		if(!buyproductService.CheckBuyproductExist(buyProdId)) {
			result = new Result();
			result.setSuccessful(false);
			result.setErrCode("ERR_REC_NOTFND");
			result.setErrMsg("¼ÇÂ¼Î´ÕÒµ½");
			ReturnJson(response, result);
			return false;
		} else {
			buyproductService.UpdateBuyPrice(buyproduct);
		}
		
 
		result = new Result();
		result.setSuccessful(true);
		return true;
	}	
}
