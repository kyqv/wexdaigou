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
import com.wex.model.Customer;
import com.wex.util.ObjUtil;

public class CustomerDao {
	DB db;
	
	/*构造方法*/
	public CustomerDao() throws SQLException{
		db = new DB();
	}
	
	public boolean CheckCustomerExist(String customerId) throws SQLException{
		String sql = "select count(*) as rowCnt from customer where customerId = ?;";

		int id = Integer.parseInt(customerId);
		ResultSet rs = db.select(sql,id);
		rs.next();
		int rowCnt = rs.getInt("rowCnt");
		if(rowCnt > 0){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean CheckCustomerExistByMobile(String mobile) throws SQLException{

		String sql = "select count(*) as rowCnt from customer where mobile = ?;";
 
		ResultSet rs = db.select(sql,mobile);
		rs.next();
		int rowCnt = rs.getInt("rowCnt");
		if(rowCnt > 0){
			return true;
		} else {
			return false;
		}
	}
	
 	public Customer getCustomerById(String customerId) throws SQLException{
		String sql = "select * from customer where customerId = ?;";
		Customer customer = new Customer();
		int id = Integer.parseInt(customerId);
		ResultSet rs = db.select(sql,id);
		rs.next();
		customer.setWeixin(rs.getString("weixin"));
		customer.setAddress(rs.getString("address"));
		customer.setName(rs.getString("name"));
		customer.setCustomerId(rs.getString("customerId"));
		customer.setMobile(rs.getString("mobile"));

		return customer;
	}
	
	public int AddCustomer(Customer customer) throws SQLException{
		String sql = "insert into customer(weixin,name,address,mobile) ";
		sql += "values(?,?,?,?);";
		
		String weixin = customer.getWeixin();
		String name = customer.getName();
		String address = customer.getAddress();
		String mobile = customer.getMobile();
		
		return db.update(sql,weixin,name,address,mobile);
	}
	
	public int DelCustomer(String customerId) throws SQLException{
		String sql = "delete from customer where customerId = ?;";
		int id = Integer.parseInt(customerId);
		return db.update(sql,id);
	}	
	
	public int UpdateCustomer(Customer customer) throws SQLException{
		String sql = "update customer set weixin=?";
		sql += ",name=?";
		sql += ",address=?";
		sql += ",mobile=?";
		sql += " where customerId = ?;";
		
		String weixin = customer.getWeixin();
		String name = customer.getName();
		String address = customer.getAddress();
		String mobile = customer.getMobile();
		int id = Integer.parseInt(customer.getCustomerId());
		return db.update(sql,weixin,name,address,mobile,id);
	}	
	
	public int UpdateCustomerByMobile(Customer customer) throws SQLException{
		String sql = "update customer set weixin=?";
		sql += ",name=?";
		sql += ",address=?";
		sql += " where mobile = ?;";
		
		String weixin = customer.getWeixin();
		String name = customer.getName();
		String address = customer.getAddress();
		String mobile = customer.getMobile();
		return db.update(sql,weixin,name,address,mobile);
	}
	
	public List<Customer> getCustomerList(String start, String cnt) throws SQLException{
		return getCustomerList(start, cnt, "");
	}
	
 	public List<Customer> getCustomerList(String start, String cnt, String searchTxt) throws SQLException{
		String sql = "select * from customer";
		if(!ObjUtil.isEmpty(searchTxt)){
			sql += " where weixin like ?";
			sql += " or name like ?";
			sql += " or mobile like ?";
		}
		sql += " order by customerId desc limit ?,?;";
		Customer customer; 
		List<Customer> customerList = new ArrayList<Customer>();
		
 		int intS = Integer.parseInt(start);
		int intC = Integer.parseInt(cnt);
		
		ResultSet rs;
		if(!ObjUtil.isEmpty(searchTxt)){
			searchTxt = "%" + searchTxt + "%";
			rs = db.select(sql,searchTxt,searchTxt,searchTxt,intS,intC);
		} else {
			rs = db.select(sql,intS,intC);
		}
		while(rs.next()) {
			customer = new Customer();
			//System.out.println(rs.getString("weixin"));
			customer.setWeixin(rs.getString("weixin"));
			customer.setAddress(rs.getString("address"));
			customer.setName(rs.getString("name"));
			customer.setCustomerId(rs.getString("customerId"));
			customer.setMobile(rs.getString("mobile"));
			customerList.add(customer);
		}
		
		return customerList;
	}
 	
 	public String getLastInsertId() throws SQLException{
		String sql = "select max(customerId) as id from customer;";
		ResultSet rs = db.select(sql);
		rs.next();
		return rs.getString("id");
	}

}
