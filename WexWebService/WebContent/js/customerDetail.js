/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */
var customerId = 0;


$(document).ready(function() {
	
	chkLogin();
	
	//get customer Id
	var prev = window.location.search.substring(1);
	var prevArray = prev.split('&');
	for(var i=0; i<prevArray.length; i++){
		var indexPair = prevArray[i].split('=');
		if(indexPair[0]=='customerId'){ customerId = getInt(indexPair[1]);}
	}

	if(customerId != 0){
		loadPage();
	} else {
		$('#btnDel').remove();
	}
	
});


function loadPage(){

	var url = rootUrl + "customerAction?action=get&customerId=" + customerId;
	ajaxGet(url);
	if(!ajaxSuccess){
		return;
	}
	
	$('#more').remove();
	
	$('#weixin').val(jsonData.weixin);
	$('#name').val(jsonData.name);
	$('#mobile').val(jsonData.mobile);
	$('#address').val(jsonData.address);
	
	
}

function del(){
	var url = rootUrl + "customerAction?action=del&customerId=" + customerId;
	ajaxGet(url);
	if(ajaxSuccess){
		location.href="customer.html";
	}
}

function edit(){
	var weixin = $('#weixin').val();
	var name = $('#name').val();
	var mobile = $('#mobile').val();
	var address = $('#address').val();
	
	if((weixin=="") || (name=="") || (mobile=="") || (address=="")){
		showAlert(msg02);
		return;
	}
	
	var json = {customerId:customerId,weixin:weixin,name:name,mobile:mobile,address:address};
	var jsonData = JSON.stringify(json);
 
	var mydata = {action:'edit',jsonData:jsonData};
	var url = rootUrl + "customerAction";
	ajaxPost(url,mydata);
	if(ajaxSuccess){
		location.href="customer.html";
	}
}
 
           
 