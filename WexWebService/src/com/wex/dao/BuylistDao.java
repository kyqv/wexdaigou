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
import com.wex.util.ObjUtil;

public class BuylistDao {
	DB db;
	
	/*构造方法*/
	public BuylistDao() throws SQLException{
		db = new DB();
	}
	
	public boolean CheckBuylistExist(String buyId) throws SQLException{
		int id = Integer.parseInt(buyId);
		String sql = "select count(*) as rowCnt from buylist where buyId=?;";

		ResultSet rs = db.select(sql,id);
		rs.next();
		int rowCnt = rs.getInt("rowCnt");
		if(rowCnt > 0){
			return true;
		} else {
			return false;
		}
	}
	
 	public Buylist getBuylistById(String buyId) throws SQLException{
		int id = Integer.parseInt(buyId);
		String sql = "select * from buylist where buyId=?;";
		Buylist buylist = new Buylist();
		ResultSet rs = db.select(sql,id);
		rs.next();
		buylist.setBuyId(rs.getString("buyId"));
		buylist.setDate(rs.getString("date"));
		buylist.setBuyPrice(rs.getString("buyPrice"));
		buylist.setSellPrice(rs.getString("sellPrice"));
		buylist.setMonth(rs.getString("month"));
		buylist.setYear(rs.getString("year"));
		buylist.setCnt(rs.getString("cnt"));
		buylist.setStatus(rs.getString("status"));				

		return buylist;
	}
	
	public int AddBuylist(Buylist buylist) throws SQLException{
		String sql = "insert into buylist(date,buyPrice,sellPrice,month,year,cnt,status)";
		sql += " values(?,?,?,?,?,?,?);";
		
		String date = buylist.getDate();
		String month = buylist.getMonth();
		String year = buylist.getYear();
		String status = buylist.getStatus();
		int buyPrice = Integer.parseInt(buylist.getBuyPrice());
		int sellPrice = Integer.parseInt(buylist.getSellPrice());
		int cnt = Integer.parseInt(buylist.getCnt());
		
		return db.update(sql,date,buyPrice,sellPrice,month,year,cnt,status);
	}
	
	public int DelBuylist(String buyId) throws SQLException{
		int id = Integer.parseInt(buyId);
		String sql = "delete from buylist where buyId=?;";
		return db.update(sql,id);
	}	
	
	public int UpdateBuylist(Buylist buylist) throws SQLException{
		String sql = "update buylist set date=?";
		sql += ",month=?";
		sql += ",year=?";
		sql += ",status=?";
		sql += " where buyId=?;";
		
		int id = Integer.parseInt(buylist.getBuyId());
		String date = buylist.getDate();
		String month = buylist.getMonth();
		String year = buylist.getYear();
		String status = buylist.getStatus();
 
		
		return db.update(sql,date,month,year,status,id);
	}	
	
	public int UpdateBuylistPrice(Buylist buylist) throws SQLException{
		String sql = "update buylist set buyPrice=?";
		sql += ",sellPrice=?";
		sql += ",cnt=?";
		sql += " where buyId=?;";
		
		int id = Integer.parseInt(buylist.getBuyId());
		int buyPrice = Integer.parseInt(buylist.getBuyPrice());
		int sellPrice = Integer.parseInt(buylist.getSellPrice());
		int cnt = Integer.parseInt(buylist.getCnt());
		
		return db.update(sql,buyPrice,sellPrice,cnt,id);
	}	
	
	public List<Buylist> getBuylistList(String start, String cnt) throws SQLException{
		return getBuylistList(start, cnt, "");
	}
	
 	public List<Buylist> getBuylistList(String start, String cnt, String searchTxt) throws SQLException{
		int intS = Integer.parseInt(start);
		int intC = Integer.parseInt(cnt);
 		
 		String sql = "select * from buylist";
		if(!ObjUtil.isEmpty(searchTxt)){
			sql += " where date like ? ";
			sql += " or month like ? ";
			sql += " or year like ? ";
			sql += " or status like ? ";
		}
		sql += " order by buyId desc limit ?,? ;";
		Buylist buylist; 
		List<Buylist> buylistList = new ArrayList<Buylist>();

		ResultSet rs;
		if(!ObjUtil.isEmpty(searchTxt)){
			searchTxt = "%" + searchTxt + "%";
			rs = db.select(sql,searchTxt,searchTxt,searchTxt,searchTxt,intS,intC);
		} else {
			rs = db.select(sql,intS,intC);
		}
			
		while(rs.next()) {
			buylist = new Buylist();
			buylist.setBuyId(rs.getString("buyId"));
			buylist.setDate(rs.getString("date"));
			buylist.setBuyPrice(rs.getString("buyPrice"));
			buylist.setSellPrice(rs.getString("sellPrice"));
			buylist.setMonth(rs.getString("month"));
			buylist.setYear(rs.getString("year"));
			buylist.setCnt(rs.getString("cnt"));
			buylist.setStatus(rs.getString("status"));					
			buylistList.add(buylist);
		}
		return buylistList;
	}
 	
 	public String getLastInsertId() throws SQLException{
		String sql = "select max(buyId) as id from buylist;";

		ResultSet rs = db.select(sql);
		rs.next();
		return rs.getString("id");
	}

}
