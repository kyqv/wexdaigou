/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */
package com.wex.service;

import java.sql.SQLException;
import java.util.List;
import com.wex.dao.CustomerDao;
import com.wex.model.Customer;

public class CustomerService {
	CustomerDao customerDao;
	
	public CustomerService() throws SQLException{
		customerDao = new CustomerDao();
	}
	
	public boolean CheckCustomerExist(String customerId) throws SQLException{
		return customerDao.CheckCustomerExist(customerId);
	}
 
	public boolean CheckCustomerExistByMobile(String mobile) throws SQLException{
		return customerDao.CheckCustomerExistByMobile(mobile);
	}
 
	
	public Customer getCustomerById(String customerId) throws SQLException{
		if(customerDao.CheckCustomerExist(customerId)) {
			return customerDao.getCustomerById(customerId);
		}else{
			return new Customer();
		}
	}
	
	public int AddCustomer(Customer customer) throws SQLException{
		return customerDao.AddCustomer(customer);
	}
	
	public int DelCustomer(String customerId) throws SQLException{
		return customerDao.DelCustomer(customerId);
	}	
	
	public int UpdateCustomer(Customer customer) throws SQLException{
		return customerDao.UpdateCustomer(customer);
	}	
	
	public int UpdateCustomerByMobile(Customer customer) throws SQLException{
		return customerDao.UpdateCustomerByMobile(customer);
	}	
	
	public List<Customer> getCustomerList(String start, String cnt) throws SQLException{
		return customerDao.getCustomerList(start, cnt);
	}
	
	public List<Customer> getCustomerList(String start, String cnt, String searchTxt) throws SQLException{
		return customerDao.getCustomerList(start, cnt, searchTxt);
	}
	
	public String getLastInsertId() throws SQLException{
		return customerDao.getLastInsertId();
	}
 

}
