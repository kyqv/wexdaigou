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
import com.wex.model.Buyorder;
import com.wex.model.OrderShip;
import com.wex.util.ObjUtil;

public class BuyorderDao {
	DB db;
	
	/*构造方法*/
	public BuyorderDao() throws SQLException{
		db = new DB();
	}
	
	public boolean CheckBuyorderExist(String orderId) throws SQLException{
		int id = Integer.parseInt(orderId);
		String sql = "select count(*) as rowCnt from buyorder where orderId=?;";

		ResultSet rs = db.select(sql,id);
		rs.next();
		int rowCnt = rs.getInt("rowCnt");
		if(rowCnt > 0){
			return true;
		} else {
			return false;
		}
		
	}
	
 	public Buyorder getBuyorderById(String orderId) throws SQLException{
		int id = Integer.parseInt(orderId);
		String sql = "select * from buyorder where orderId=?;";
		Buyorder buyorder = new Buyorder();
		ResultSet rs = db.select(sql,id);
		rs.next();
		buyorder.setBuyId(rs.getString("buyId"));
		buyorder.setWeixin(rs.getString("weixin"));
		buyorder.setAddress(rs.getString("address"));
		buyorder.setName(rs.getString("name"));
		buyorder.setOrderId(rs.getString("orderId"));
		buyorder.setMobile(rs.getString("mobile"));
		buyorder.setBuyPrice(rs.getString("buyPrice"));
		buyorder.setSellPrice(rs.getString("sellPrice"));
		buyorder.setCnt(rs.getString("cnt"));
		buyorder.setStatus(rs.getString("status"));		
		//buyorder.setShip(rs.getString("ship"));		
		//buyorder.setShipNo(rs.getString("shipNo"));		
		//buyorder.setShipImg(rs.getString("shipImg"));		
		
		
 		return buyorder;
	}
	
	public int AddBuyorder(Buyorder buyorder) throws SQLException{
		String sql = "insert into buyorder(buyId,weixin,name,address,mobile,buyPrice,sellPrice,cnt,status)";
		sql += " values(?,?,?,?,?,?,?,?,?);";
		
		int id = Integer.parseInt(buyorder.getBuyId());
		String weixin = buyorder.getWeixin();
		String name = buyorder.getName();
		String address = buyorder.getAddress();
		String mobile = buyorder.getMobile();
		String status = buyorder.getStatus();
		int buyPrice = Integer.parseInt(buyorder.getBuyPrice());
		int sellPrice = Integer.parseInt(buyorder.getSellPrice());
		int cnt = Integer.parseInt(buyorder.getCnt());
		
		return db.update(sql,id,weixin,name,address,mobile,buyPrice,sellPrice,cnt,status);
	}
	
	public int DelBuyorder(String orderId) throws SQLException{
		int id = Integer.parseInt(orderId);
		String sql = "delete from buyorder where orderId=?;";
		return db.update(sql,id);
	}	
	
	public int DelBuyorderByBuy(String buyId) throws SQLException{
		int id = Integer.parseInt(buyId);
		String sql = "delete from buyorder where buyId=?;";
		return db.update(sql,id);
	}	
	
	public int UpdateBuyorder(Buyorder buyorder) throws SQLException{
		String sql = "update buyorder set weixin=?";
		sql += ",name=?";
		sql += ",address=?"; 
		sql += ",mobile=?"; 
		sql += ",status=?";
		sql += " where orderId = ?;";
		
		int id = Integer.parseInt(buyorder.getOrderId());
		String weixin = buyorder.getWeixin();
		String name = buyorder.getName();
		String address = buyorder.getAddress();
		String mobile = buyorder.getMobile();
		String status = buyorder.getStatus();
 		
		return db.update(sql,weixin,name,address,mobile,status,id);
	}	
	
	public int UpdateBuyorderPrice(Buyorder buyorder) throws SQLException{
		String sql = "update buyorder set buyPrice=?";
		sql += ",sellPrice=?";
		sql += ",cnt=?";
		sql += " where orderId = ?;";
		
		int id = Integer.parseInt(buyorder.getOrderId());
		int buyPrice = Integer.parseInt(buyorder.getBuyPrice());
		int sellPrice = Integer.parseInt(buyorder.getSellPrice());
		int cnt = Integer.parseInt(buyorder.getCnt());
		
		return db.update(sql,buyPrice,sellPrice,cnt,id);
	}	
	
	public int UpdateStatus(Buyorder buyorder) throws SQLException{
		String sql = "update buyorder set status=?";
		sql += " where orderId = ?;";
		
		int id = Integer.parseInt(buyorder.getOrderId());
		String status = buyorder.getStatus();
		
		return db.update(sql,status,id);
	}
	
	public List<Buyorder> getBuyorderList(String buyId, String start, String cnt) throws SQLException{
		return getBuyorderList(buyId, start, cnt, "");
	}
	
 	public List<Buyorder> getBuyorderList(String buyId, String start, String cnt, String searchTxt) throws SQLException{
		
		String sql = "select * from buyorder where buyId=?";
		if(!ObjUtil.isEmpty(searchTxt)){
			sql += " and ( weixin like ?";
			sql += " or name like ?";
			sql += " or mobile like ?";
			sql += " or status like ?)";
		}
		sql += " order by orderId desc limit ?,?;";

		Buyorder buyorder; 
		List<Buyorder> buyorderList = new ArrayList<Buyorder>();
 		
		int id = Integer.parseInt(buyId);
 		int intS = Integer.parseInt(start);
		int intC = Integer.parseInt(cnt);
		
		ResultSet rs;
		if(!ObjUtil.isEmpty(searchTxt)){
			searchTxt = "%" + searchTxt + "%";
			rs = db.select(sql,id,searchTxt,searchTxt,searchTxt,searchTxt,intS,intC);
		} else {
			rs = db.select(sql,id,intS,intC);
		}
		
		while(rs.next()) {
			buyorder = new Buyorder();
			buyorder.setBuyId(rs.getString("buyId"));
			buyorder.setWeixin(rs.getString("weixin"));
			buyorder.setAddress(rs.getString("address"));
			buyorder.setName(rs.getString("name"));
			buyorder.setOrderId(rs.getString("orderId"));
			buyorder.setMobile(rs.getString("mobile"));
			buyorder.setBuyPrice(rs.getString("buyPrice"));
			buyorder.setSellPrice(rs.getString("sellPrice"));
			buyorder.setCnt(rs.getString("cnt"));
			buyorder.setStatus(rs.getString("status"));
			//buyorder.setShip(rs.getString("ship"));		
			//buyorder.setShipNo(rs.getString("shipNo"));						
			buyorderList.add(buyorder);
		}
 
		return buyorderList;
	}
 	
 	public String getLastInsertId() throws SQLException{
		String sql = "select max(orderId) as id from buyorder;";
		ResultSet rs = db.select(sql);
		rs.next();
		return rs.getString("id");
	}
 	
 	
	public int UpdateOrderShip(OrderShip orderShip) throws SQLException{
		String sql = "update ordership set ship=?";
		sql += ",shipNo=?";
		sql += ",shipImg=?";
		sql += " where orderId = ?;";
		
		int id = Integer.parseInt(orderShip.getOrderId());
		String ship = orderShip.getShip();
		String shipNo = orderShip.getShipNo();
		String shipImg = orderShip.getShipImg();
		
		return db.update(sql,ship,shipNo,shipImg,id);
	}
	
	public int DelOrderShip(String orderId) throws SQLException{
		String sql = "delete from ordership";
		sql += " where orderId = ?;";
		
		return db.update(sql,orderId);
	}
	
 	public OrderShip getOrderShip(String orderId) throws SQLException{
		int id = Integer.parseInt(orderId);
		String sql = "select * from ordership where orderId=?;";
		OrderShip orderShip = new OrderShip();
		ResultSet rs = db.select(sql,id);
		rs.next();
		orderShip.setOrderId(rs.getString("orderId"));
		orderShip.setShip(rs.getString("ship"));		
		orderShip.setShipNo(rs.getString("shipNo"));		
		orderShip.setShipImg(rs.getString("shipImg"));		
			
 		return orderShip;
	}
 	
	public int AddOrderShip(OrderShip orderShip) throws SQLException{
		String sql = "insert into ordership(orderId,ship,shipNo,shipImg)";
		sql += " values(?,?,?,?);";
		
		int id = Integer.parseInt(orderShip.getOrderId());
		String ship = orderShip.getShip();
		String shipNo = orderShip.getShipNo();
		String shipImg = orderShip.getShipImg();
		
		return db.update(sql,id,ship,shipNo,shipImg);
	}
	
	public boolean CheckOrderShipExist(String orderId) throws SQLException{
		int id = Integer.parseInt(orderId);
		String sql = "select count(*) as rowCnt from ordership where orderId=?;";

		ResultSet rs = db.select(sql,id);
		rs.next();
		int rowCnt = rs.getInt("rowCnt");
		if(rowCnt > 0){
			return true;
		} else {
			return false;
		}
		
	}
	
	public int DelOrderShipByBuy(String buyId) throws SQLException{
		int id = Integer.parseInt(buyId);
		String sql = "delete from ordership where orderId in (select orderId from buyorder where buyId=?);";
		return db.update(sql,id);
	}	
}
