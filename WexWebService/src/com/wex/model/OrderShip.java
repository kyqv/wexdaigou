/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */
package com.wex.model;

public class OrderShip {
	private String orderId;
	private String ship;
	private String shipNo;
 	private String shipImg;
 	
	public String getShipNo() {
		return shipNo;
	}
	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getShip() {
		return ship;
	}
	public void setShip(String ship) {
		this.ship = ship;
	}
	public String getShipImg() {
		return shipImg;
	}
	public void setShipImg(String shipImg) {
		this.shipImg = shipImg;
	}
 
 
}
