/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */
package com.wex.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wex.common.DB;
import com.wex.model.TopCategory;
import com.wex.model.TopCustomer;
import com.wex.model.TopProduct;
import com.wex.model.TopTime;
import com.wex.util.ObjUtil;


public class ReportDao {
	DB db;
	
	/*构造方法*/
	public ReportDao() throws SQLException{
		db = new DB();
	}
	
	public List<TopCategory> getTopCategory(String orderBy) throws SQLException{
		
		String sqlOrderBy = orderBy.replace(";", "").replace("'", "");
		 		
		String sql = "select category,sum(buyPrice) as buyPrice, sum(sellPrice) as sellPrice,sum(sellPrice) - sum(buyPrice) as gain, sum(cnt) as cnt from buyproduct group by category ";
		if(!ObjUtil.isEmpty(orderBy)) {
			sql += " order by " + sqlOrderBy;
		}
		
		sql += " limit 0,50;";
		
		TopCategory topCategory; 
		List<TopCategory> topCategoryList = new ArrayList<TopCategory>();
		
		ResultSet rs = db.select(sql);
		while(rs.next()) {
			topCategory = new TopCategory();
			topCategory.setBuyPrice(rs.getString("buyPrice"));
			topCategory.setSellPrice(rs.getString("sellPrice"));
			topCategory.setCategory(rs.getString("category"));
			topCategory.setGain(rs.getString("gain"));
			topCategory.setProductCnt(rs.getString("cnt"));
			topCategoryList.add(topCategory);
		}
		return topCategoryList;
	}
		
	public List<TopProduct> getTopProduct(String category, String orderBy) throws SQLException{
		String sqlOrderBy = orderBy.replace(";", "").replace("'", "");
		
		String sql = "";
		if(!ObjUtil.isEmpty(category)){
			sql += "select prodName, category,sum(buyPrice) as buyPrice, sum(sellPrice) as sellPrice,sum(sellPrice) - sum(buyPrice) as gain, sum(cnt) as cnt from buyproduct ";
			sql += "where 1=1 ";
			sql += "and category=? ";
			sql += "group by prodName,category ";
		} else {
			sql += "select prodName, '' as category, sum(buyPrice) as buyPrice, sum(sellPrice) as sellPrice,sum(sellPrice) - sum(buyPrice) as gain, sum(cnt) as cnt from buyproduct ";
			sql += "where 1=1 ";
			sql += "group by prodName ";			
		}		
		
		if(!ObjUtil.isEmpty(orderBy)) {
			sql += " order by " + sqlOrderBy;
		}
		
		sql += " limit 0,50;";
		
		TopProduct topProduct; 
		List<TopProduct> topProductList = new ArrayList<TopProduct>();

		ResultSet rs;
		
		if(!ObjUtil.isEmpty(category)){
			rs = db.select(sql,category);
		}else{
			rs = db.select(sql);
		}
		
		while(rs.next()) {
			topProduct = new TopProduct();
			topProduct.setProdName(rs.getString("prodName"));
			topProduct.setBuyPrice(rs.getString("buyPrice"));
			topProduct.setSellPrice(rs.getString("sellPrice"));
			topProduct.setGain(rs.getString("gain"));;
			topProduct.setCategory(rs.getString("category"));
			topProduct.setProductCnt(rs.getString("cnt"));
			topProductList.add(topProduct);
		}
		return topProductList;
	}

 	public List<TopCustomer> getTopCustomer(String category, String orderBy) throws SQLException{
		String sqlOrderBy = orderBy.replace(";", "").replace("'", "");
		
 		String sql = "";
		if(!ObjUtil.isEmpty(category)){
			sql += "select B.name, B.weixin, B.mobile, A.category,sum(A.buyPrice) as buyPrice, sum(A.sellPrice) as sellPrice, sum(A.sellPrice) - sum(A.buyPrice) as gain, sum(A.cnt) as cnt from buyproduct A, buyorder B ";
			sql += "where 1=1 ";
			sql += "and A.orderId = B.orderId ";			
			sql += "and A.category=? ";
			sql += "group by name,weixin,mobile,category ";
		} else {
			sql += "select B.name, B.weixin, B.mobile, '' as category, sum(A.buyPrice) as buyPrice, sum(A.sellPrice) as sellPrice, sum(A.sellPrice) - sum(A.buyPrice) as gain, sum(A.cnt) as cnt from buyproduct A, buyorder B ";
			sql += "where 1=1 ";
			sql += "and A.orderId = B.orderId ";
			sql += "group by name,weixin,mobile ";			
		}
		
		if(!ObjUtil.isEmpty(orderBy)) {
			sql += " order by " + sqlOrderBy;
		}
		
		sql += " limit 0,50;";
		
		TopCustomer topCustomer; 
		List<TopCustomer> topCustomerList = new ArrayList<TopCustomer>();
 
		ResultSet rs;
		
		if(!ObjUtil.isEmpty(category)){
			rs = db.select(sql,category);
		}else{
			rs = db.select(sql);
		}
		while(rs.next()) {
			topCustomer = new TopCustomer();
			topCustomer.setWeixin(rs.getString("weixin"));
			//topCustomer.setAddress(rs.getString("address"));
			topCustomer.setName(rs.getString("name"));
			topCustomer.setMobile(rs.getString("mobile"));
			topCustomer.setBuyPrice(rs.getString("buyPrice"));
			topCustomer.setSellPrice(rs.getString("sellPrice"));
			topCustomer.setGain(rs.getString("gain"));				
			topCustomer.setProductCnt(rs.getString("cnt"));
			topCustomer.setCategory(rs.getString("category"));
			topCustomerList.add(topCustomer);
		}
		return topCustomerList;
	}	

 	public List<TopTime> getTopYear(String category, String orderBy) throws SQLException{
		
		String sqlOrderBy = orderBy.replace(";", "").replace("'", "");
		
 		String sql = "";
		if(!ObjUtil.isEmpty(category)){
			sql += "select C.year, A.category,sum(A.buyPrice) as buyPrice, sum(A.sellPrice) as sellPrice, sum(A.sellPrice) - sum(A.buyPrice) as gain, sum(A.cnt) as cnt ";
			sql += "from buyproduct A, buyorder B, buylist C ";
			sql += "where A.orderId = B.orderId and C.buyId = B.buyId ";
			sql += "and A.category=? ";
			sql += "group by year,category ";
		} else {
			sql += "select C.year, sum(A.buyPrice) as buyPrice, sum(A.sellPrice) as sellPrice, sum(A.sellPrice) - sum(A.buyPrice) as gain, sum(A.cnt) as cnt ";
			sql += "from buyproduct A, buyorder B, buylist C ";
			sql += "where A.orderId = B.orderId and C.buyId = B.buyId ";
			sql += "group by year ";		
		}
		
		if(!ObjUtil.isEmpty(orderBy)) {
			sql += " order by " + sqlOrderBy;
		}
		sql += " limit 0,50;";
		
		TopTime topTime; 
		List<TopTime> topTimeList = new ArrayList<TopTime>();
 
		ResultSet rs;
		
		if(!ObjUtil.isEmpty(category)){
			rs = db.select(sql,category);
		}else{
			rs = db.select(sql);
		}
		while(rs.next()) {
			topTime = new TopTime();
			//topCustomer.setAddress(rs.getString("address"));
			topTime.setTime(rs.getString("year"));
			topTime.setBuyPrice(rs.getString("buyPrice"));
			topTime.setSellPrice(rs.getString("sellPrice"));
			topTime.setGain(rs.getString("gain"));				
			topTime.setProductCnt(rs.getString("cnt"));
			topTimeList.add(topTime);
		}
		return topTimeList;
	}		
 
 	public List<TopTime> getTopMonth(String category, String orderBy) throws SQLException{
		String sqlOrderBy = orderBy.replace(";", "").replace("'", "");
		
		String sql = "";
		if(!ObjUtil.isEmpty(category)){
			sql += "select C.month, A.category,sum(A.buyPrice) as buyPrice, sum(A.sellPrice) as sellPrice, sum(A.sellPrice) - sum(A.buyPrice) as gain, sum(A.cnt) as cnt ";
			sql += "from buyproduct A, buyorder B, buylist C ";
			sql += "where A.orderId = B.orderId and C.buyId = B.buyId ";
			sql += "and A.category=? ";
			sql += "group by month,category ";
		} else {
			sql += "select C.month, sum(A.buyPrice) as buyPrice, sum(A.sellPrice) as sellPrice, sum(A.sellPrice) - sum(A.buyPrice) as gain, sum(A.cnt) as cnt ";
			sql += "from buyproduct A, buyorder B, buylist C ";
			sql += "where A.orderId = B.orderId and C.buyId = B.buyId ";
			sql += "group by month ";		
		}
		
		if(!ObjUtil.isEmpty(orderBy)) {
			sql += " order by " + sqlOrderBy;
		}
		
		sql += " limit 0,50;";
		
		TopTime topTime; 
		List<TopTime> topTimeList = new ArrayList<TopTime>();

		ResultSet rs;
		
		if(!ObjUtil.isEmpty(category)){
			rs = db.select(sql,category);
		}else{
			rs = db.select(sql);
		}
		while(rs.next()) {
			topTime = new TopTime();
			//topCustomer.setAddress(rs.getString("address"));
			topTime.setTime(rs.getString("month"));
			topTime.setBuyPrice(rs.getString("buyPrice"));
			topTime.setSellPrice(rs.getString("sellPrice"));
			topTime.setGain(rs.getString("gain"));				
			topTime.setProductCnt(rs.getString("cnt"));
			topTimeList.add(topTime);
		}
		return topTimeList;
	}	
}
