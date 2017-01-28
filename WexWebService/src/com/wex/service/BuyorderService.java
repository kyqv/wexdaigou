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
import com.wex.model.Buyorder;
import com.wex.model.OrderShip;
import com.wex.util.ObjUtil;

public class BuyorderService {
	BuyproductDao buyproductDao;
	BuyorderDao buyorderDao;
	BuylistDao buylistDao;
	
	public BuyorderService() throws SQLException{
		buyproductDao = new BuyproductDao();
		buyorderDao = new BuyorderDao();
		buylistDao = new BuylistDao();
	}
	
	public boolean CheckBuyorderExist(String orderId) throws SQLException{
		return buyorderDao.CheckBuyorderExist(orderId);
	}
 
	public Buyorder getBuyorderById(String orderId) throws SQLException{
		if(buyorderDao.CheckBuyorderExist(orderId)) {
			return buyorderDao.getBuyorderById(orderId);
		}else{
			return new Buyorder();
		}
	}
	
	public int AddBuyorder(Buyorder buyorder) throws SQLException{
		return buyorderDao.AddBuyorder(buyorder);
	}
	
	public int DelBuyorder(String orderId) throws SQLException{
		//return buyorderDao.DelBuyorder(orderId);
		Buyorder buyorder = buyorderDao.getBuyorderById(orderId);
		if(ObjUtil.isEmpty(buyorder)) {
			return 0;
		}
		String buyId = buyorder.getBuyId();
		if(buyorderDao.DelBuyorder(orderId) > 0){
			buyproductDao.DelBuyproductByOrder(orderId);
			Buylist buylist = buyproductDao.getPriceByBuylist(buyId);
			buylistDao.UpdateBuylistPrice(buylist);
		}
		
		buyorderDao.DelOrderShip(orderId);
		
		return 1;
		
	}	
	
	public int UpdateBuyorder(Buyorder buyorder) throws SQLException{
		return buyorderDao.UpdateBuyorder(buyorder);
	}	
	

	public List<Buyorder> getBuyorderList(String buyId, String start, String cnt) throws SQLException{
		return buyorderDao.getBuyorderList(buyId,start, cnt);
	}
	
	public List<Buyorder> getBuyorderList(String buyId, String start, String cnt, String searchTxt) throws SQLException{
		return buyorderDao.getBuyorderList(buyId, start, cnt, searchTxt);
	}
 
	public String getLastInsertId() throws SQLException{
		return buyorderDao.getLastInsertId();
	}
	
	public int UpdateStatus(Buyorder buyorder) throws SQLException{
		return buyorderDao.UpdateStatus(buyorder);
	}
	
	public int UpdateOrderShip(OrderShip orderShip) throws SQLException{
		String orderId = orderShip.getOrderId();
		if(buyorderDao.CheckOrderShipExist(orderId)){
			return buyorderDao.UpdateOrderShip(orderShip);
		} else {
			return buyorderDao.AddOrderShip(orderShip);
		}	
	}	
	
	public int AddOrderShip(OrderShip orderShip) throws SQLException{
		return buyorderDao.AddOrderShip(orderShip);
	}	
	
	public int DelOrderShip(String orderId) throws SQLException{
		return buyorderDao.DelOrderShip(orderId);
	}	 
 
	public OrderShip getOrderShip(String orderId) throws SQLException{
		if(buyorderDao.CheckOrderShipExist(orderId)){
			return buyorderDao.getOrderShip(orderId);
		}else{
			return new OrderShip();
		}
	}
}
