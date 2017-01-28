/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */
package com.wex.model;

public class Buyproduct {
	private String buyProdId;
	private String orderId;
	private String prodName;
 	private String buyPrice;
 	private String sellPrice;
 	private String category;
 	private String cnt;
 	private String status; 	
 	private String shop;
 
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public String getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(String buyPrice) {
		this.buyPrice = buyPrice;
	}
	public String getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(String sellPrice) {
		this.sellPrice = sellPrice;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getBuyProdId() {
		return buyProdId;
	}
	public void setBuyProdId(String buyProdId) {
		this.buyProdId = buyProdId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCnt() {
		return cnt;
	}
	public void setCnt(String cnt) {
		this.cnt = cnt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getShop() {
		return shop;
	}
	public void setShop(String shop) {
		this.shop = shop;
	}
 
 
}
