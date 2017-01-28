/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */
$(document).ready(function() {
	
	chkLogin();
 	
});
 
function edit(){
	var buyDate = $('#buyDate').val();
	
	if(buyDate.length == 0){
		showAlert(msg01);
		return;
	}
	

	var mydata = {action:'edit',buyDate:buyDate, buyId:0};
	var url = "buylistAction";
	ajaxPost(url,mydata);
	if(ajaxSuccess){
		location.href="buy.html";
	}
}
 
           
 