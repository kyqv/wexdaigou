/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */
var buyId = 0;

$(document).ready(function() {
	
	chkLogin();

	var prev = window.location.search.substring(1);
	var prevArray = prev.split('&');
	for(var i=0; i<prevArray.length; i++){
		var indexPair = prevArray[i].split('=');
		if(indexPair[0]=='buyId'){ buyId = getInt(indexPair[1]);}
	}

	if(buyId != 0){
		loadPage();
	} 
});

function loadPage(){

	var url = rootUrl + "buylistAction?action=get&buyId=" + buyId;
	ajaxGet(url);
	if(!ajaxSuccess){
		return;
	}
	
	$('#buyDate').val(jsonData.date);
	$('#status').val(jsonData.status);
	
	
} 

function edit(){
	var buyDate = $('#buyDate').val();
	var status = $('#status').val();
	
	if(buyDate.length == 0){
		showAlert(msg01);
		return;
	}
	

	var mydata = {action:'edit',buyDate:buyDate, status:status, buyId:buyId};
	var url = rootUrl + "buylistAction";
	ajaxPost(url,mydata);
	if(ajaxSuccess){
		location.href="buy.html";
	}
}
 
           
 