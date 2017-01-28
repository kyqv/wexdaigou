/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */
package com.wex.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.wex.model.Result;
import com.wex.model.User;


public class ValidateUtil {

 	public static Result NotEmpty(String str) {
		Result result = new Result();
		result.setSuccessful(true);
		if(ObjUtil.isEmpty(str)){
			result.setSuccessful(false);
			result.setErrCode("ERR_INV_EMPTY");
			result.setErrMsg("字段不可以为空 (" + str.toString() + ")");
		}
		return result;
	}
 	
 	public static Result MaxLength(String str, int maxLen) {
		Result result = new Result();
		result.setSuccessful(true);
		if(str.length() > maxLen){
			result.setSuccessful(false);
			result.setErrCode("ERR_INV_MAXLEN");
			result.setErrMsg("字段长度超出限制,最大长度为:" + maxLen + "(" + str.toString() + ")");
		}
		return result;
	}
 	
 	public static Result MinLength(String str, int minLen) {
		Result result = new Result();
		result.setSuccessful(true);
		if(str.length() < minLen){
			result.setSuccessful(false);
			result.setErrCode("ERR_INV_MINLEN");
			result.setErrMsg("字段长度不够,最短长度为:" + minLen + "(" + str.toString() + ")");
		}
		return result;
	}

 	public static Result isInt(String str) {
		Result result = new Result();
		result.setSuccessful(true);
		try{
			Integer.parseInt(str);
		}catch(NumberFormatException e){
			result.setSuccessful(false);
			result.setErrCode("ERR_INV_INT");
			result.setErrMsg("字段必须是整数(" + str.toString() + ")");
		}
		return result;
	}

	public static Result CheckLogin(HttpServletRequest request){
		Result result = new Result();
		result.setSuccessful(true);
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if(ObjUtil.isEmpty(user)){
			result.setSuccessful(false);
			result.setErrCode("ERR_INV_SESSION");
			result.setErrMsg("请先登录");
		}
		return result;
	}
 
	public static Result CheckAction(String action, List<String> actionList){
		Result result = new Result();
		result.setSuccessful(true);
		
		if(!actionList.contains(action)){
			result.setSuccessful(false);
			result.setErrCode("ERR_INV_ACTION");
			result.setErrMsg("无效操作");
		}
 
		return result;
	}
	
 	public static Result isYYYYMMDD(String str) {
		Result result = new Result();
		result.setSuccessful(true);
		try{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			format.setLenient(false);
			format.parse(str);
		}catch(ParseException e){
			result.setSuccessful(false);
			result.setErrCode("ERR_INV_DATE");
			result.setErrMsg("日期格式不合法(必须是YYYY-MM-DD)");
		}
		return result;
	}
 	
	public static boolean protectLogin(String userId, HttpSession session){
		Object objRt = session.getAttribute(userId + "-Retry");
		if(ObjUtil.isEmpty(objRt)){
			session.setAttribute(userId + "-Retry", 1);
			return true;
		}
		
		if(!(objRt instanceof Number)){
			session.setAttribute(userId + "-Retry", 1);
			return true;
		}
		
		Number n = (Number) objRt;
		int intRt = n.intValue();
		if(intRt >= 5) {
			return false;
		}
		
		intRt = intRt + 1;
		session.setAttribute(userId + "-Retry", intRt);
				
		return true;
		
	}
}
