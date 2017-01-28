/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */
package com.wex.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class CcyUtil {
	
	public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
 
    //配置您申请的KEY
    public static final String APPKEY ="be6f012fbf900b20643643ee2714aa0c";
 
    
    //1.人民币牌价
    public static String getRmbEx() throws SQLException{
        String result = null;
        String url ="http://web.juhe.cn:8080/finance/exchange/rmbquot";//请求接口地址
        Map<String,String> params = new HashMap<String, String>();//请求参数
        params.put("key",APPKEY);//APP Key
        params.put("type","");//两种格式(0或者1,默认为0)
 
        try {
            result = net(url, params, "GET");
        	//System.out.println("result="+result);
            return result;
        } catch (Exception e) {
            //e.printStackTrace();
        	System.out.println(e.getMessage());
        	SQLException se = new SQLException("获取汇率失败,将使用默认汇率");
        	throw se;
        }
    }
 
    //2.外汇汇率
    public static String getUsdEX() throws SQLException{
        String result = null;
        String url ="http://web.juhe.cn:8080/finance/exchange/frate";//请求接口地址
        Map<String,String> params = new HashMap<String, String>();//请求参数
        params.put("key",APPKEY);//APP Key
        params.put("type","");//两种格式(0或者1,默认为0)
 
        try {
            result =net(url, params, "GET");
            return result;
            /*
            JSONObject object = JSONObject.fromObject(result);
            if(object.getInt("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
            */
        } catch (Exception e) {
            e.printStackTrace();
        	SQLException se = new SQLException(e.getMessage());
        	throw se;
        }
    }
    
    /**
    *
    * @param strUrl 请求地址
    * @param params 请求参数
    * @param method 请求方法
    * @return  网络请求字符串
    * @throws Exception
    */
   public static String net(String strUrl, Map<String, String> params,String method) throws Exception {
       HttpURLConnection conn = null;
       BufferedReader reader = null;
       String rs = null;
       try {
           StringBuffer sb = new StringBuffer();
           if(method==null || method.equals("GET")){
               strUrl = strUrl+"?"+urlencode(params);
           }
           URL url = new URL(strUrl);
           conn = (HttpURLConnection) url.openConnection();
           if(method==null || method.equals("GET")){
               conn.setRequestMethod("GET");
           }else{
               conn.setRequestMethod("POST");
               conn.setDoOutput(true);
           }
           conn.setRequestProperty("User-agent", userAgent);
           conn.setUseCaches(false);
           conn.setConnectTimeout(DEF_CONN_TIMEOUT);
           conn.setReadTimeout(DEF_READ_TIMEOUT);
           conn.setInstanceFollowRedirects(false);
           conn.connect();
           if (params!= null && method.equals("POST")) {
               try (DataOutputStream out = new DataOutputStream(conn.getOutputStream())) {
                   out.writeBytes(urlencode(params));
               }
           }
           InputStream is = conn.getInputStream();
           reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
           String strRead = null;
           while ((strRead = reader.readLine()) != null) {
               sb.append(strRead);
           }
           rs = sb.toString();
       } catch (IOException e) {
           //e.printStackTrace();
    	   throw e;
       } finally {
           if (reader != null) {
               reader.close();
           }
           if (conn != null) {
               conn.disconnect();
           }
       }
       return rs;
   }

   //将map型转为请求参数型
   public static String urlencode(Map<String, String> params) throws UnsupportedEncodingException {
       StringBuilder sb = new StringBuilder();
       for (Map.Entry<String,String> i : params.entrySet()) {
           try {
               sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
           } catch (UnsupportedEncodingException e) {
               //e.printStackTrace();
               throw e;
           }
       }
       return sb.toString();
   }
}
