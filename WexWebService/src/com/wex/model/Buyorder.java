/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */
package com.wex.model;

public class Buyorder {
	private String orderId;
	private String buyId;
	private String weixin;
 	private String name;
 	private String address;
 	private String mobile;
 	private String buyPrice;
 	private String sellPrice; 	
 	private String cnt;
 	private String status; 	
 	private String ship;
 	private String shipNo;
 	private String shipImg;
 	
	public String getWeixin() {
		return weixin;
	}
	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
 
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
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
	public String getBuyId() {
		return buyId;
	}
	public void setBuyId(String buyId) {
		this.buyId = buyId;
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
	public String getShip() {
		return ship;
	}
	public void setShip(String ship) {
		this.ship = ship;
	}
	public String getShipNo() {
		return shipNo;
	}
	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}
	public String getShipImg() {
		return shipImg;
	}
	public void setShipImg(String shipImg) {
		this.shipImg = shipImg;
	}
 
}
