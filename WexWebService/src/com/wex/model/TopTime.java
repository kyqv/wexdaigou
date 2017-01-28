/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */
package com.wex.model;

public class TopTime {
	private String time;
 	private String buyPrice;
 	private String sellPrice;
 	private String gain;
 	private String category;
 	private String orderCnt;
 	private String productCnt;
 	
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
 
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getOrderCnt() {
		return orderCnt;
	}
	public void setOrderCnt(String orderCnt) {
		this.orderCnt = orderCnt;
	}
	public String getProductCnt() {
		return productCnt;
	}
	public void setProductCnt(String productCnt) {
		this.productCnt = productCnt;
	}
	public String getGain() {
		return gain;
	}
	public void setGain(String gain) {
		this.gain = gain;
	}
 
 
 
}
