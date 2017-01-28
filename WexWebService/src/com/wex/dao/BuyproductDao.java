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
import com.wex.model.Buylist;
import com.wex.model.Buyorder;
import com.wex.model.Buyproduct;


public class BuyproductDao {
	DB db;
	
	/*构造方法*/
	public BuyproductDao() throws SQLException{
		db = new DB();
	}
	
	public boolean CheckBuyproductExist(String buyProdId) throws SQLException{
		int id = Integer.parseInt(buyProdId);
		String sql = "select count(*) as rowCnt from buyproduct where buyProdId = ?;";
		ResultSet rs = db.select(sql,id);
		rs.next();
		int rowCnt = rs.getInt("rowCnt");
		if(rowCnt > 0){
			return true;
		} else {
			return false;
		}
	}
	
 	public Buyproduct getBuyproductById(String buyProdId) throws SQLException{
		int id = Integer.parseInt(buyProdId);
		String sql = "select * from buyproduct where buyProdId = ?;";
 
		Buyproduct buyproduct = new Buyproduct();
		ResultSet rs = db.select(sql,id);
		rs.next();
		buyproduct.setBuyProdId(rs.getString("buyProdId"));
		buyproduct.setProdName(rs.getString("prodName"));
		buyproduct.setBuyPrice(rs.getString("buyPrice"));
		buyproduct.setSellPrice(rs.getString("sellPrice"));
		buyproduct.setCategory(rs.getString("category"));
		buyproduct.setOrderId(rs.getString("orderId"));
		buyproduct.setCnt(rs.getString("cnt"));
		buyproduct.setStatus(rs.getString("status"));
		buyproduct.setShop(rs.getString("shop"));
		return buyproduct;
	}
	
	public int AddBuyproduct(Buyproduct buyproduct) throws SQLException{
		String sql = "insert into buyproduct(prodName,buyPrice,sellPrice,category,orderId,cnt,status,shop) ";
		sql += "values(?,?,?,?,?,?,?,?);";
		
		String prodName = buyproduct.getProdName();
		String category = buyproduct.getCategory();
		String status = buyproduct.getStatus();
		String shop = buyproduct.getShop();
		int buyPrice = Integer.parseInt(buyproduct.getBuyPrice());
		int sellPrice = Integer.parseInt(buyproduct.getSellPrice());
		int cnt = Integer.parseInt(buyproduct.getCnt());
		int orderId = Integer.parseInt(buyproduct.getOrderId());
		
		return db.update(sql,prodName,buyPrice,sellPrice,category,orderId,cnt,status,shop);
	}
	
	public int DelBuyproduct(String buyProdId) throws SQLException{
		int id = Integer.parseInt(buyProdId);
		String sql = "delete from buyproduct where buyProdId = ?;";
		return db.update(sql,id);
	}	
	
	public int DelBuyproductByOrder(String orderId) throws SQLException{
		int id = Integer.parseInt(orderId);
		String sql = "delete from buyproduct where orderId = ?";
		return db.update(sql,id);
	}	
	
	public int DelBuyproductByBuy(String buyId) throws SQLException{
		int id = Integer.parseInt(buyId);
		String sql = "delete from buyproduct where orderId in (select orderId from buyorder where buyId=?);";
		return db.update(sql,id);
	}	
	
	public int UpdateBuyproduct(Buyproduct buyproduct) throws SQLException{
		String sql = "update buyproduct set prodName=?";
		sql += ",category=?";
		sql += ",buyPrice=?";
		sql += ",sellPrice=?";
		sql += ",cnt=?";
		sql += ",status=?";
		sql += ",shop=?";
		sql += " where buyProdId=?;";
		
		String prodName = buyproduct.getProdName();
		String category = buyproduct.getCategory();
		String status = buyproduct.getStatus();
		String shop = buyproduct.getShop();
		int buyPrice = Integer.parseInt(buyproduct.getBuyPrice());
		int sellPrice = Integer.parseInt(buyproduct.getSellPrice());
		int cnt = Integer.parseInt(buyproduct.getCnt());
		int id = Integer.parseInt(buyproduct.getBuyProdId());
		
		return db.update(sql,prodName,category,buyPrice,sellPrice,cnt,status,shop,id);
	}	
	
	public List<Buyproduct> getBuyproductListByOrder(String orderId) throws SQLException{
		String sql = "select * from buyproduct where orderId=?";
		sql += " order by category,prodName";
		Buyproduct buyproduct; 
		List<Buyproduct> buyproductList = new ArrayList<Buyproduct>();
		
		int id = Integer.parseInt(orderId);
		ResultSet rs = db.select(sql,id);
		while(rs.next()) {
			buyproduct = new Buyproduct();
			buyproduct.setBuyProdId(rs.getString("buyProdId"));
			buyproduct.setProdName(rs.getString("prodName"));
			buyproduct.setBuyPrice(rs.getString("buyPrice"));
			buyproduct.setSellPrice(rs.getString("sellPrice"));
			buyproduct.setCategory(rs.getString("category"));
			buyproduct.setOrderId(rs.getString("orderId"));
			buyproduct.setCnt(rs.getString("cnt"));
			buyproduct.setStatus(rs.getString("status"));
			buyproduct.setShop(rs.getString("shop"));
			buyproductList.add(buyproduct);
		}
		return buyproductList;
	}
	
	public List<Buyproduct> getBuyproductListByBuylist(String buyId) throws SQLException{
		String sql = "select A.* from buyproduct A, buyorder B where A.orderId = B.orderId and B.buyId=?";
		sql += " and A.status='待采购'";
		sql += " order by shop,category,prodName";
		Buyproduct buyproduct; 
		List<Buyproduct> buyproductList = new ArrayList<Buyproduct>();

		int id = Integer.parseInt(buyId);
		ResultSet rs = db.select(sql,id);
		while(rs.next()) {
			buyproduct = new Buyproduct();
			buyproduct.setBuyProdId(rs.getString("buyProdId"));
			buyproduct.setProdName(rs.getString("prodName"));
			buyproduct.setBuyPrice(rs.getString("buyPrice"));
			buyproduct.setSellPrice(rs.getString("sellPrice"));
			buyproduct.setCategory(rs.getString("category"));
			buyproduct.setOrderId(rs.getString("orderId"));
			buyproduct.setCnt(rs.getString("cnt"));
			buyproduct.setStatus(rs.getString("status"));		
			buyproduct.setShop(rs.getString("shop"));
			buyproductList.add(buyproduct);
		}
		return buyproductList;
	}
	
	
 	public Buyorder getPriceByOrder(String orderId) throws SQLException{
		String sql = "select sum(buyPrice * cnt) as buyPrice,sum(sellPrice * cnt) as sellPrice, sum(cnt) as cnt from buyproduct where orderId=?;";
		Buyorder buyorder = new Buyorder();
		buyorder.setBuyPrice("0");
		buyorder.setSellPrice("0");
		buyorder.setCnt("0");
 
		int id = Integer.parseInt(orderId);
		ResultSet rs = db.select(sql,id);
		rs.next();
		buyorder.setOrderId(orderId);
		String buyPrice = rs.getString("buyPrice");
		String sellPrice = rs.getString("sellPrice");
		String cnt = rs.getString("cnt");
		buyPrice = (buyPrice==null)?"0":buyPrice;
		sellPrice = (sellPrice==null)?"0":sellPrice;
		cnt = (cnt==null)?"0":cnt;
			
		buyorder.setBuyPrice(buyPrice);
		buyorder.setSellPrice(sellPrice);
		buyorder.setCnt(cnt);

		return buyorder;
	}

 	public Buylist getPriceByBuylist(String buyId) throws SQLException{
		String sql = "select sum(A.buyPrice * A.cnt) as buyPrice,sum(A.sellPrice * A.cnt) as sellPrice, sum(A.cnt) as cnt  from buyproduct A, buyorder B where A.orderId=B.orderId and B.buyId=?;";
		Buylist buylist = new Buylist();
		buylist.setBuyPrice("0");
		buylist.setSellPrice("0");
 
		int id = Integer.parseInt(buyId);
		ResultSet rs = db.select(sql,id);
		rs.next();
		buylist.setBuyId(buyId);
		String buyPrice = rs.getString("buyPrice");
		String sellPrice = rs.getString("sellPrice");
		String cnt = rs.getString("cnt");
			
		buyPrice = (buyPrice==null)?"0":buyPrice;
		sellPrice = (sellPrice==null)?"0":sellPrice;
		cnt = (cnt==null)?"0":cnt;
			
		buylist.setBuyPrice(buyPrice);
		buylist.setSellPrice(sellPrice);
		buylist.setCnt(cnt);		

		return buylist;
	}
 	
	public int UpdateBuyPrice(Buyproduct buyproduct) throws SQLException{
		String sql = "update buyproduct set ";
		sql += "buyPrice=?";
		sql += ",status=?";
		sql += " where buyProdId=?;";
		
		String status = buyproduct.getStatus();
		int buyPrice = Integer.parseInt(buyproduct.getBuyPrice());
		int id = Integer.parseInt(buyproduct.getBuyProdId());
		
		return db.update(sql,buyPrice,status,id);
	}	
 	
 	public String getLastInsertId() throws SQLException{
		String sql = "select max(buyProdId) as id from buyproduct;";
 
		ResultSet rs = db.select(sql);
		rs.next();
		return rs.getString("id");
	}
}
