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
import com.wex.model.Product;
import com.wex.model.ProductImg;
import com.wex.util.ObjUtil;

public class ProductDao {
	DB db;
	
	/*构造方法*/
	public ProductDao() throws SQLException{
		db = new DB();
	}
	
	public boolean CheckProductExist(String prodId) throws SQLException{
		int id = Integer.parseInt(prodId);
		String sql = "select count(*) as rowCnt from product where prodId = ?";

		ResultSet rs = db.select(sql,id);
		rs.next();
		int rowCnt = rs.getInt("rowCnt");
		if(rowCnt > 0){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean CheckProductExistByName(String prodName) throws SQLException{
		String sql = "select count(*) as rowCnt from product where prodName = ?;";

		ResultSet rs = db.select(sql, prodName);
		rs.next();
		int rowCnt = rs.getInt("rowCnt");
		if(rowCnt > 0){
			return true;
		} else {
			return false;
		}
	}
	
 	public Product getProductById(String prodId) throws SQLException{
		int id = Integer.parseInt(prodId);
		String sql = "select * from product where prodId = ?;";
		Product product = new Product();
		ResultSet rs = db.select(sql,id);
		rs.next();
		product.setProdId(rs.getString("prodId"));
		product.setProdName(rs.getString("prodName"));
		product.setBuyPrice(rs.getString("buyPrice"));
		product.setSellPrice(rs.getString("sellPrice"));
		product.setCategory(rs.getString("category"));
		//product.setImg(rs.getString("img"));
		product.setShop(rs.getString("shop"));
		return product;
	}
	
	public int AddProduct(Product product) throws SQLException{
		String sql = "insert into product(prodName,buyPrice,sellPrice,category,shop) values(?,?,?,?,?);";
		String prodName = product.getProdName();
		String category = product.getCategory();
		String shop = product.getShop();
		int buyPrice = Integer.parseInt(product.getBuyPrice());
		int sellPrice = Integer.parseInt(product.getSellPrice());
		return db.update(sql, prodName,buyPrice,sellPrice,category,shop);
 
	}
	

	public int DelProduct(String prodId) throws SQLException{
		int id = Integer.parseInt(prodId);
		String sql = "delete from product where prodId = ?";
		return db.update(sql,id);
	}	
	
	public int UpdateProduct(Product product) throws SQLException{
		String sql = "update product set prodName= ?";
		sql += ",buyPrice=?";
		sql += ",sellPrice=?";
		sql += ",category=?";
		sql += ",shop=?";
		sql += " where prodId = ?;";
		
		int id = Integer.parseInt(product.getProdId());
		String prodName = product.getProdName();
		String category = product.getCategory();
		String shop = product.getShop();
		int buyPrice = Integer.parseInt(product.getBuyPrice());
		int sellPrice = Integer.parseInt(product.getSellPrice());
		
		return db.update(sql,prodName,buyPrice,sellPrice,category,shop,id);
	}
	
	public int UpdateBuyPriceByName(Product product) throws SQLException{
		String sql = "update product set buyPrice=?";
		sql += " where prodName = ?;";
		
		String prodName = product.getProdName();
		int buyPrice = Integer.parseInt(product.getBuyPrice());
		
		return db.update(sql,buyPrice, prodName);
	}
	
	public int UpdateProductByName(Product product) throws SQLException{
		String sql = "update product set ";
		sql += " buyPrice=?";
		sql += ",sellPrice=?";
		sql += ",category=?";
		sql += ",shop=?";		
		sql += " where prodName = ?;";
		
		String prodName = product.getProdName();
		String category = product.getCategory();
		String shop = product.getShop();
		int buyPrice = Integer.parseInt(product.getBuyPrice());
		int sellPrice = Integer.parseInt(product.getSellPrice());
 		
		return db.update(sql,buyPrice,sellPrice,category,shop,prodName);
	}
	
	public List<Product> getProductList(String start, String cnt) throws SQLException{
		return getProductList(start, cnt, "");
	}
	
 	public List<Product> getProductList(String start, String cnt, String searchTxt) throws SQLException{
		String sql = "select * from product";
		if(!ObjUtil.isEmpty(searchTxt)){
			sql += " where prodName like ?";
			sql += " or category like ?";
			sql += " or shop like ?";			
		}
		sql += " order by prodId desc limit ?,?;";
 
		Product product; 
		List<Product> productList = new ArrayList<Product>();
		
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
			product = new Product();
			//System.out.println(rs.getString("weixin"));
			product.setProdId(rs.getString("prodId"));
			product.setProdName(rs.getString("prodName"));
			product.setBuyPrice(rs.getString("buyPrice"));
			product.setSellPrice(rs.getString("sellPrice"));
			product.setCategory(rs.getString("category"));
			//product.setImg(rs.getString("img"));		
			product.setShop(rs.getString("shop"));
			productList.add(product);
		}
		return productList;
	}

 	public String getLastInsertId() throws SQLException{
		String sql = "select max(prodId) as id from product;";
		ResultSet rs = db.select(sql);
		rs.next();
		return rs.getString("id");
	}
 	
	public int UpdateProductImg(ProductImg productImg) throws SQLException{
		String sql = "update productimg set img=?";
		sql += " where prodId = ?;";
		
		int id = Integer.parseInt(productImg.getProdId());
		String img = productImg.getImg();
		
		return db.update(sql,img,id);
	}
	
	public int DelProductImg(String prodId) throws SQLException{
		String sql = "delete from productimg";
		sql += " where prodId = ?;";

		return db.update(sql,prodId);
	}
	
 	public ProductImg getProductImg(String prodId) throws SQLException{
		int id = Integer.parseInt(prodId);
		String sql = "select * from productimg where prodId = ?;";
		ProductImg productImg = new ProductImg();
		ResultSet rs = db.select(sql,id);
		rs.next();
		productImg.setProdId(rs.getString("prodId"));
		productImg.setImg(rs.getString("img"));
		return productImg;
	}
 	
	public int AddProductImg(ProductImg productImg) throws SQLException{
		String sql = "insert into productimg(prodId,img) values(?,?);";
		int id = Integer.parseInt(productImg.getProdId());
		String img = productImg.getImg();
		return db.update(sql,id,img);
 
	}
	
	public boolean CheckProductImgExist(String prodId) throws SQLException{
		int id = Integer.parseInt(prodId);
		String sql = "select count(*) as rowCnt from productimg where prodId = ?";

		ResultSet rs = db.select(sql,id);
		rs.next();
		int rowCnt = rs.getInt("rowCnt");
		if(rowCnt > 0){
			return true;
		} else {
			return false;
		}
	}
}
