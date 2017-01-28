/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */

	
function jsonAjaxPost(){
	var userId=$("#userId").val();
	var password=$("#password").val();
	//showAlert(hex_md5(password));
	//return;
	password = hex_md5(password);
	//var url = rootUrl + "loginAction?action=login&userId="+encodeURI(encodeURI(userId))+"&password="+encodeURI(encodeURI(password));
	var url = rootUrl + "loginAction";
	var mydata = {action:"login",userId:userId,password:password};
 
	ajaxPost(url,mydata);

	if(ajaxSuccess){
		if(prevUrl != ""){
			location.href = prevUrl;
		} else {
			location.href="index.html";
		}
	} 
	
}
           
 