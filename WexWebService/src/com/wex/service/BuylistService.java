/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */
package com.wex.service;

import java.sql.SQLException;
import java.util.List;

import com.wex.dao.BuylistDao;
import com.wex.dao.BuyorderDao;
import com.wex.dao.BuyproductDao;
import com.wex.model.Buylist;

public class BuylistService {
	BuyproductDao buyproductDao;
	BuyorderDao buyorderDao;
	BuylistDao buylistDao;
	
	public BuylistService() throws SQLException{
		buyproductDao = new BuyproductDao();
		buyorderDao = new BuyorderDao();
		buylistDao = new BuylistDao();
	}
	
	public boolean CheckBuylistExist(String buyId) throws SQLException{
		return buylistDao.CheckBuylistExist(buyId);
	}
 
	public Buylist getBuylistById(String buyId) throws SQLException{
		if(buylistDao.CheckBuylistExist(buyId)) {
			return buylistDao.getBuylistById(buyId);
		}else{
			return new Buylist();
		}
	}
	
	public int AddBuylist(Buylist Buylist) throws SQLException{
		return buylistDao.AddBuylist(Buylist);
	}
	
	public int DelBuylist(String buyId) throws SQLException{
		if(buylistDao.DelBuylist(buyId) > 0){
			buyproductDao.DelBuyproductByBuy(buyId);
			buyorderDao.DelOrderShipByBuy(buyId);
			buyorderDao.DelBuyorderByBuy(buyId);
		}
		return 1;
	}	
	
	public int UpdateBuylist(Buylist Buylist) throws SQLException{
		return buylistDao.UpdateBuylist(Buylist);
	}	
	
	public List<Buylist> getBuylistList(String start, String cnt) throws SQLException{
		return buylistDao.getBuylistList(start, cnt);
	}
	
	public List<Buylist> getBuylistList(String start, String cnt, String searchTxt) throws SQLException{
		return buylistDao.getBuylistList(start, cnt, searchTxt);
	}
 
	public String getLastInsertId() throws SQLException{
		return buylistDao.getLastInsertId();
	}
 

}
