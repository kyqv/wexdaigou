/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */
var start = 0;
var searchTxt = $('#searchTxt').val();

var strCusItemSample = " <li class='item' id='cus-item'>" + $('#cus-item').html() + "</li>";
var strLine = "<li class='line'></li>";
var strMore = "<li class='more' id='more'>" + $('#more').html() + "</li>";
var $cusList = $('#cusList');


$(document).ready(function() {
	chkLogin();
	
	$cusList.html("");

	loadPage();
});

function loadPage(){
	closeAlert();
	$('#more').remove();

	var url = rootUrl + "customerAction";
	var mydata={action:'list',start:start,cnt:cnt,searchTxt:searchTxt};
	ajaxPost(url,mydata);
	if(!ajaxSuccess){
		return;
	}
 
	var strCusItem = "";
	for(var i=0;i<jsonData.length;i++){
		customerId = jsonData[i].customerId;
		name = jsonData[i].name;
		address=jsonData[i].address;
		weixin=jsonData[i].weixin;
		mobile=jsonData[i].mobile;
		customerName = name + " (" + weixin + ")";
		strCusItem = strCusItemSample.replace(/\{customerId}/g, customerId);
		strCusItem = strCusItem.replace(/\{customerName}/g, customerName);
		strCusItem = strCusItem.replace(/\{mobile}/g, mobile);
		strCusItem = strCusItem.replace(/\{address}/g, address);
		$cusList.append(strCusItem);
		$cusList.append(strLine);
	}
	
	if(jsonData.length == cnt) {
		$cusList.append(strMore);
	}
	
	start = start + cnt;
	
}

function loadMore(){
	loadPage();
}

function btnSearch(){
	searchTxt = $('#searchTxt').val();
	start = 0;
	
	$cusList.html("");
	loadPage();
}

function del(customerId){
	var url = rootUrl + "customerAction?action=del&customerId=" + customerId;
	ajaxGet(url);
	if(ajaxSuccess){
		location.href="customer.html";
	}
}
 