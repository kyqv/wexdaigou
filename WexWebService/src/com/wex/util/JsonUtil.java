/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */
package com.wex.util;


import java.util.List;

import com.alibaba.fastjson.JSON;

 

public class JsonUtil {

	public static Object Json2Entity(String jsonStr, Class<?> cls) {
		 return JSON.parseObject(jsonStr, cls);
	}


	public static String Entity2Json(Object obj) {
		return JSON.toJSONString(obj, true);
	}
 
	public static String List2Json(List<Object> objList) {
		return JSON.toJSONString(objList, true);
	}

	
}
