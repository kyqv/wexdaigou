/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */
package com.wex.util;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtil {
 

    public static boolean delete(String fileName) {  
    	File file = new File(fileName);
    	if(!file.exists()){
    		return false;
    	}else{
    		if(file.isFile()){
    			return deleteFile(fileName);
    		}else{
    			return deleteDirectory(fileName);
    		}
    	}
    
    }
    
    public static boolean deleteFile(String fileName) {  
    	File file = new File(fileName);
    	if(file.exists() && file.isFile()){
    		file.delete();
    		return true;
    	}else{
    		return false;
    	}
    
    }
    
    public static boolean deleteDirectory(String dir) {  
    	if(!dir.endsWith(File.separator)){
    		dir = dir + File.separator;
    	}
    	
    	File file = new File(dir);
    	
    	if(!file.exists() || !file.isDirectory()){
    		return false;
    	}
    	
    	return true;
    
    }
    
    public static boolean deleteLogFile(String logPath, String keepDays) {  
    	//SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
    	
 		int days = 360;
	 	try {
	 		days = Integer.parseInt(keepDays);
	 	}catch(NumberFormatException e){
	 		e.printStackTrace();
	 	}
	 	 	
	    Date dt = new Date();
	    
 
			    
	    Long sec = dt.getTime()/1000  - days * 24 * 60 * 60;
	    
	    dt.setTime(sec * 1000);
	    	    
 
    	
    	File file = new File(logPath);
    	
    	if(!file.exists() || !file.isDirectory()){
    		return false;
    	}
    	
    	File logFile = null;
    	String logFileName = "";
    	
    	String[] fileList = file.list();
    	for(int i = 0; i < fileList.length; i ++){
        	if(logPath.endsWith(File.separator)){
        		logFileName = logPath + fileList[i];
        	}else{
        		logFileName = logPath + File.separator + fileList[i];
        	}
        	logFile = new File(logFileName);
        	if(logFile.isFile() && isDeleteLog(logFileName, dt)){
        		logFile.delete();
 
        	}
    	}
    	
    	return true;
    }
    
 public static boolean isDeleteLog(String logFileName, Date keepDate) {  
	 	SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
 
	 	String strFileDate = logFileName.substring(logFileName.lastIndexOf(".") + 1);
	    if (!strFileDate.equals("log")){
    		try {
    			Date fileDate = myFormat.parse(strFileDate);
		    	
 
		    	//logger.info("File time=" + fileDate.getTime());
		    	//logger.info("keep time=" + keepDate.getTime());
		    	
				if (fileDate.getTime() < keepDate.getTime()){
					return true;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
	    }
    	
    	return false;
    }
    
}