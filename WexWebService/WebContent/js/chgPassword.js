/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */

$(document).ready(function() {
	chkLogin();
});

function jsonAjaxPost(){
	//var userId=$("#userId").val();
	var password=$("#password").val();
	var newPassword=$("#newPassword").val();
	var dupPassword=$("#dupPassword").val();
	if(newPassword != dupPassword){
		showAlert("重复密码不正确!");
		return;
	}
	if(password.length ==0){
		showAlert("旧密码不能为空!");
		return;
	}
	if(newPassword.length ==0){
		showAlert("新密码不能为空!");
		return;
	}
	if((newPassword.length < 6)||(newPassword.length > 10)){
		showAlert("新密码的长度必须是6到10位之间!");
		return;
	}
		
	//showAlert(hex_md5(password));
	//return;
	password = hex_md5(password);
	newPassword = hex_md5(newPassword);
	dupPassword = hex_md5(dupPassword);
	
	var url = rootUrl + "chgPasswordAction";
	var mydata={password:password,newPassword:newPassword,dupPassword:dupPassword};
	ajaxPost(url,mydata);
	if(ajaxSuccess){
		location.href="login.html";
	} 
	
}
           
 