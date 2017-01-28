/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */
var orderId=0;
var buyId=0;

var start = 0;
var searchTxt = $('#searchTxt').val();

var strCusItemSample = " <li class='head' id='cus-item'>" + $('#cus-item').html() + "</li>";
var strLine = "<li class='line'></li>";
var strMore = "<li class='more' id='more'>" + $('#more').html() + "</li>";
var $cusList = $('#cusList');

$(document).ready(function() {
	chkLogin();
	
	//get order Id
	var prev = window.location.search.substring(1);
	var prevArray = prev.split('&');
	for(var i=0; i<prevArray.length; i++){
		var indexPair = prevArray[i].split('=');
		if(indexPair[0]=='orderId'){ orderId = getInt(indexPair[1]);}
		if(indexPair[0]=='buyId'){ buyId = getInt(indexPair[1]);}
	}

	
	if(orderId != 0){
		loadBuyorder();
	} else {
		if(buyId == 0){
			location.href="index.html";
		}
	}	
	 
	$cusList.html("");
	
	href = "buyorder.html?buyId=" + buyId;
	$('#hrefOrder').attr("href",href);
	
	loadBuylist();
	loadPage();
});

function loadBuyorder(){
	var url = rootUrl + "buyorderAction?action=get&orderId=" + orderId;
	ajaxGet(url);
	if(!ajaxSuccess){
		return;
	}
	
	buyId = jsonData.buyId;

	
	$('#weixin').val(jsonData.weixin);
	$('#name').val(jsonData.name);
	$('#mobile').val(jsonData.mobile);
	$('#address').val(jsonData.address);
	$('#buyPrice').val(jsonData.buyPrice);
	$('#sellPrice').val(jsonData.sellPrice);
	$('#cnt').val(jsonData.cnt);
	$('#status').val(jsonData.status);
}

function loadBuylist(){
	var url = rootUrl + "buylistAction?action=get&buyId=" + buyId;
	ajaxGet(url);
	if(!ajaxSuccess){
		return;
	}
	
	$('#date').val(jsonData.date);

	href = "selectproduct.html?orderId=" + orderId;
	$('#hrefSelectproduct').attr('href', href);
}
 
function del(){
	var url = rootUrl + "buyorderAction?action=del&orderId=" + orderId;
	ajaxGet(url);
	if(ajaxSuccess){
		location.href="buyorder.html?buyId="+buyId;
	}
}
 
function edit(){
	var weixin = $('#weixin').val();
	var name = $('#name').val();
	var mobile = $('#mobile').val();
	var address = $('#address').val();
	var buyPrice = $('#buyPrice').val();
	var sellPrice = $('#sellPrice').val();
	var cusCnt = $('#cnt').val();
	var status = $('#status').val();
	
	if((weixin=="") || (name=="") || (mobile=="") || (address=="")){
		showAlert(msg02);
		return;
	}
	
	var json = {orderId:orderId,buyId:buyId,weixin:weixin,name:name,mobile:mobile,address:address,status:status,buyPrice:buyPrice,sellPrice:sellPrice,cnt:cusCnt};
	var jsonData = JSON.stringify(json);

	var mydata = {action:'edit',jsonData:jsonData};
	var url = rootUrl + "buyorderAction";
	ajaxPost(url,mydata);
	if(ajaxSuccess){
		location.href="buyorder.html?buyId=" + buyId;
	}
}           

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
		//customerName = name + "," + mobile;
		
		strCusItem = strCusItemSample.replace(/\{customerId}/g, customerId);
		strCusItem = strCusItem.replace(/\{customerName}/g, customerName);
		strCusItem = strCusItem.replace(/\{mobile}/g, mobile);
		//strCusItem = strCusItem.replace(/\{mobile}/g, weixin);
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

function select(customerId){
	var url = rootUrl + "customerAction?action=get&customerId=" + customerId;
	ajaxGet(url);
	if(!ajaxSuccess){
		return;
	}
	
	$('#weixin').val(jsonData.weixin);
	$('#name').val(jsonData.name);
	$('#mobile').val(jsonData.mobile);
	$('#address').val(jsonData.address);
	
}
