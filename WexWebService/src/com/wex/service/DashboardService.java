/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */
package com.wex.service;
 
import java.sql.SQLException;

import com.wex.dao.DashboardDao;
import com.wex.model.Dashboard;
 

public class DashboardService {
	DashboardDao dashboardDao;
	
	public DashboardService() throws SQLException{
		dashboardDao = new DashboardDao();
	}
	
	public Dashboard getDashboard() throws SQLException{
		Dashboard dashboard = new Dashboard();
		
		String buylistCnt = dashboardDao.getBuylistCnt();
		String customerCnt = dashboardDao.getCustomerCnt();
		String orderCnt = dashboardDao.getBuyOrderCnt();
		String productCnt = dashboardDao.getProductCnt();
		
		dashboard.setBuylistCnt(buylistCnt);
		dashboard.setCustomerCnt(customerCnt);
		dashboard.setOrderCnt(orderCnt);
		dashboard.setProductCnt(productCnt);
		
		return dashboard;
	}
 
 
 

}
