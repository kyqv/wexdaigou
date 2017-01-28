/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */
package com.wex.service;

import java.sql.SQLException;
import java.util.List;

import com.wex.dao.ReportDao;
import com.wex.model.TopCategory;
import com.wex.model.TopCustomer;
import com.wex.model.TopProduct;
import com.wex.model.TopTime;


public class ReportService {
	ReportDao reportDao;

	public ReportService() throws SQLException{
		reportDao = new ReportDao();
	}
	
	public List<TopCategory> getTopCategory(String orderBy) throws SQLException{
		return reportDao.getTopCategory(orderBy);
	}
		
	public List<TopProduct> getTopProduct(String category, String orderBy) throws SQLException{
		return reportDao.getTopProduct(category, orderBy);
	}

 	public List<TopCustomer> getTopCustomer(String category, String orderBy) throws SQLException{
		return reportDao.getTopCustomer(category, orderBy);
	}	

 	public List<TopTime> getTopYear(String category, String orderBy) throws SQLException{
		
 		return reportDao.getTopYear(category, orderBy);
	}		
 
 	public List<TopTime> getTopMonth(String category, String orderBy) throws SQLException{
		
 		return reportDao.getTopMonth(category, orderBy);
	}	

}
