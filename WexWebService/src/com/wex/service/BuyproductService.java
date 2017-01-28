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
import com.wex.dao.ProductDao;
import com.wex.model.Buylist;
import com.wex.model.Buyorder;
import com.wex.model.Buyproduct;
import com.wex.model.Product;
import com.wex.util.ObjUtil;

public class BuyproductService {
	BuyproductDao buyproductDao;
	BuyorderDao buyorderDao;
	BuylistDao buylistDao;
	ProductDao productDao;
	
	public BuyproductService() throws SQLException{
		buyproductDao = new BuyproductDao();
		buyorderDao = new BuyorderDao();
		buylistDao = new BuylistDao();
		productDao = new ProductDao();
	}
	
	public boolean CheckBuyproductExist(String buyProdId) throws SQLException{
		return buyproductDao.CheckBuyproductExist(buyProdId);
	}
 
	public Buyproduct getBuyproductById(String buyProdId) throws SQLException{
		if(buyproductDao.CheckBuyproductExist(buyProdId)) {
			return buyproductDao.getBuyproductById(buyProdId);
		}else{
			return null;
		}
	}
	
	public int AddBuyproduct(Buyproduct buyproduct) throws SQLException{
		//return buyproductDao.AddBuyproduct(Buyproduct);

		String orderId = buyproduct.getOrderId();
		if(buyproductDao.AddBuyproduct(buyproduct) > 0){
			Buyorder buyorder = buyproductDao.getPriceByOrder(orderId);
			buyorderDao.UpdateBuyorderPrice(buyorder);
			buyorder = buyorderDao.getBuyorderById(orderId);
			String buyId = buyorder.getBuyId();
			Buylist buylist = buyproductDao.getPriceByBuylist(buyId);
			buylistDao.UpdateBuylistPrice(buylist);
		}
		return 1;
	}
	
	public int DelBuyproduct(String buyProdId) throws SQLException{
		Buyproduct buyproduct = buyproductDao.getBuyproductById(buyProdId);
		if(ObjUtil.isEmpty(buyproduct)) {
			return 0;
		}
		String orderId = buyproduct.getOrderId();
		if(buyproductDao.DelBuyproduct(buyProdId) > 0){
			Buyorder buyorder = buyproductDao.getPriceByOrder(orderId);
			buyorderDao.UpdateBuyorderPrice(buyorder);
			buyorder = buyorderDao.getBuyorderById(orderId);
			String buyId = buyorder.getBuyId();
			Buylist buylist = buyproductDao.getPriceByBuylist(buyId);
			buylistDao.UpdateBuylistPrice(buylist);
		}
		return 1;
	}	
	
	public int UpdateBuyproduct(Buyproduct buyproduct) throws SQLException{
		//return buyproductDao.UpdateBuyproduct(Buyproduct);

		String orderId = buyproduct.getOrderId();
		if(buyproductDao.UpdateBuyproduct(buyproduct) > 0){
			Buyorder buyorder = buyproductDao.getPriceByOrder(orderId);
			buyorderDao.UpdateBuyorderPrice(buyorder);
			buyorder = buyorderDao.getBuyorderById(orderId);
			String buyId = buyorder.getBuyId();
			Buylist buylist = buyproductDao.getPriceByBuylist(buyId);
			buylistDao.UpdateBuylistPrice(buylist);
		}
		return 1;
	}	
	
	public List<Buyproduct> getBuyproductListByOrder(String orderId) throws SQLException{
		return buyproductDao.getBuyproductListByOrder(orderId);
	}
	
	public List<Buyproduct> getBuyproductListByBuylist(String buyId) throws SQLException{
		return buyproductDao.getBuyproductListByBuylist(buyId);
	}
 
	
	public String getLastInsertId() throws SQLException{
		return buyproductDao.getLastInsertId();
	}
	
	public int UpdateBuyPrice(Buyproduct buyproduct) throws SQLException{
		int i = buyproductDao.UpdateBuyPrice(buyproduct);
		if( i > 0) {
			Product product = new Product();
			product.setProdName(buyproduct.getProdName());
			product.setBuyPrice(buyproduct.getBuyPrice());
			productDao.UpdateBuyPriceByName(product);
		}
		return i;
	}
 
}
