/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */
package com.wex.service;

import java.sql.SQLException;
import java.util.List;

import com.wex.dao.ProductDao;
import com.wex.model.Product;
import com.wex.model.ProductImg;

public class ProductService {
	ProductDao productDao;
	
	public ProductService() throws SQLException{
		productDao = new ProductDao();
	}
	
	public boolean CheckProductExist(String prodId) throws SQLException{
		return productDao.CheckProductExist(prodId);
	}
	
	public boolean CheckProductExistByName(String prodName) throws SQLException{
		return productDao.CheckProductExistByName(prodName);
	}
 
	public Product getProductById(String prodId) throws SQLException{
		if(productDao.CheckProductExist(prodId)) {
			return productDao.getProductById(prodId);
		}else{
			return new Product();
		}
	}
	
	public int AddProduct(Product product) throws SQLException{
		return productDao.AddProduct(product);
	}
	
	public int DelProduct(String prodId) throws SQLException{
		productDao.DelProduct(prodId);
		productDao.DelProductImg(prodId);
		return 1;		
	}	
	
	public int UpdateProduct(Product product) throws SQLException{
		return productDao.UpdateProduct(product);
	}
	
	public int UpdateProductByName(Product product) throws SQLException{
		return productDao.UpdateProductByName(product);
	}		
	
	public List<Product> getProductList(String start, String cnt) throws SQLException{
		return productDao.getProductList(start, cnt);
	}
	
	public List<Product> getProductList(String start, String cnt, String searchTxt) throws SQLException{
		return productDao.getProductList(start, cnt, searchTxt);
	}
 
	public String getLastInsertId() throws SQLException{
		return productDao.getLastInsertId();
	}
 
	
	public int UpdateProductImg(ProductImg productImg) throws SQLException{
		String prodId = productImg.getProdId();
		if (productDao.CheckProductImgExist(prodId)){
			return productDao.UpdateProductImg(productImg);
		} else {
			return productDao.AddProductImg(productImg);
		}
	}
	
	public int AddProductImg(ProductImg productImg) throws SQLException{
		return productDao.AddProductImg(productImg);
	}
	
	public int DelProductImg(String prodId) throws SQLException{
		return productDao.DelProductImg(prodId);
	}

	public ProductImg getProductImg(String prodId) throws SQLException{
		if (productDao.CheckProductImgExist(prodId)){
			return productDao.getProductImg(prodId);
		}else{
			return new ProductImg();
		}

	}
}
