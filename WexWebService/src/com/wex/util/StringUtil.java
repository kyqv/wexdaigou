/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */
package com.wex.util;

import java.util.Date;

public class StringUtil {
	public static String coverObj2String(Object obj){
		if(obj==null)return null;
		if(obj instanceof Boolean){
			boolean bl= (Boolean)obj;
			if(bl)return "1";
			return "0";
		}
		if(obj instanceof Integer){
			Integer bl= (Integer)obj;
			return String.valueOf(bl);
		}if(obj instanceof Double){
			Double bl= (Double)obj;
			return String.valueOf(bl);
		}
		 
		return (String) obj;
	}
	
	public static int coverObj2int(Object obj){
		if(obj==null)return -1;
		return (Integer) obj;
	}
	
	public static Date coverObj2Date(Object obj){
		if(obj==null)return null;
		return (Date) obj;
	}
	
	
	public static void StringBufferReplacce(StringBuffer stringbuffer,String src, String dest){
		int index_start=stringbuffer.indexOf(src);
		int index_end=index_start+src.length();
		if(dest==null)dest=src;
		stringbuffer.replace(index_start, index_end,dest);
	}
	
 

}
