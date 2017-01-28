/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */
package com.wex.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class ObjUtil
{

	private static String userIp = null;

	private static String userMachineName = null;

	private ObjUtil(){}

	/**
	 * check if the given object is null or empty
	 * 
	 * @param object
	 * @return true if null or empty
	 */
	public static Boolean isEmpty(Object object) {

		// null check
		if (object == null) {
			return true;
		}

		if (object instanceof Collection<?>) {
			Collection<?> c = (Collection<?>) object;
			return c.size() == 0;
		}

		if (object instanceof Map<?, ?>) {
			Map<?, ?> m = (Map<?, ?>) object;
			return m.size() == 0;
		}

		if (object instanceof Number) {
			Number n = (Number) object;
			return n.doubleValue() == 0.0;
		}

		// others
		return StringUtils.isEmpty(object.toString());

	}
	

	protected static String formatAmount(String myNumber, int precision) {
		if (myNumber == null)
			return "";

		double myDecimal = Double.valueOf(myNumber).doubleValue();
		StringBuffer pBuf = new StringBuffer("#,##0");
		if (precision > 0) {
			pBuf.append(".");
			for (int i = 0; i < precision; i++) {
				pBuf.append("0");
			}
		}
		DecimalFormat form = new DecimalFormat(pBuf.toString());
		return form.format(myDecimal);
	}

	protected static String formatAccountNo(String myAccountNo) {
		if (ObjUtil.isEmpty(myAccountNo))
			return "";

		myAccountNo = myAccountNo.trim();
		if (myAccountNo.length() <= 6) {
			return myAccountNo;
		} else {
			return myAccountNo.substring(0, 3) + "-"
					+ myAccountNo.substring(3, 6) + "-"
					+ myAccountNo.substring(6, myAccountNo.length());
		}
	}

	protected static String leftFillZero(String str, int length) {
		if (str == null)
			str = "000001";

		int strLength = str.length();
		if (strLength >= length) {
			return str;
		} else {
			for (int i = 0; i < (length - strLength); i++) {
				str = "0" + str;
			}
			return str;
		}
	}

	protected static String getUserIp() {
		if (userIp != null)
			return userIp;

		try {
			userIp = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			userIp = null;
		}
		return userIp;

	}

	protected static String getUserMachineName() {
		if (userMachineName != null)
			return userMachineName;

		try {
			userMachineName = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			userMachineName = null;
		}
		return userMachineName;
	}
	
	/**
	 * trim blank for the given object
	 * beta version: only support String now. 14 Sep. 2016
	 * 
	 * @param object
	 * @return "" if null or empty
	 */
	public static String trimBlank(Object object) {
		
		String ls_retStr = "";
		// null check
		if (null == object) {
			return ls_retStr;
		}
		
		if (object instanceof String) {
			ls_retStr = (""+(object.toString())).trim();
			
		}
		return ls_retStr;

	}

}


