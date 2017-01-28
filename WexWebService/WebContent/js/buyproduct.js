/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */
var orderId=0;
var buyId=0;
//var start = 0;
//var searchTxt = $('#searchTxt').val();

var strCusItemSample = " <li class='item' id='cus-item'>" + $('#cus-item').html() + "</li>";
var strLine = "<li class='line'></li>";
//var strMore = "<li class='more' id='more'>" + $('#more').html() + "</li>";
var $cusList = $('#cusList');


$(document).ready(function() {
	chkLogin();
	
	//get order Id
	var prev = window.location.search.substring(1);
	var prevArray = prev.split('&');
	for(var i=0; i<prevArray.length; i++){
		var indexPair = prevArray[i].split('=');
		if(indexPair[0]=='orderId'){ orderId = getInt(indexPair[1]);}
	}
	
	$cusList.html("");
	
	if(orderId != 0){
		loadCustomer();
		loadPage();
	} else {
		location.href="index.html";
	}
	
	href = "buyorder.html?buyId=" + buyId;
	$('#hrefOrder').attr("href",href);
	href = "selectproduct.html?orderId=" + orderId;
	$('#hrefSelectproduct').attr('href', href);
});

function loadCustomer(){
	var url = rootUrl + "buyorderAction?action=get&orderId=" + orderId;
	ajaxGet(url);
	if(!ajaxSuccess){
		return;
	}
	buyId = jsonData.buyId;
	
	strHtml = $('#buyorder').html();
	
	orderId = jsonData.orderId;
	name = jsonData.name;
	address=jsonData.address;
	weixin=jsonData.weixin;
	mobile=jsonData.mobile;
	
	status = jsonData.status;
	
	buyPrice= lblBuyPrice + jsonData.buyPrice;
	sellPrice= lblSellPrice + jsonData.sellPrice;
	disCnt = lblCnt + jsonData.cnt;
	price = disCnt + " | " + buyPrice + " | " + sellPrice;
	
	customerName = name + " (" + weixin + ")";
	
	strHtml = strHtml.replace(/\{orderId}/g, orderId);
	strHtml = strHtml.replace(/\{customerName}/g, customerName);
	strHtml = strHtml.replace(/\{mobile}/g, mobile);
	strHtml = strHtml.replace(/\{address}/g, address);
	strHtml = strHtml.replace(/\{price}/g, price);
	strHtml = strHtml.replace(/\{status}/g, status);

	var url = rootUrl + "buylistAction?action=get&buyId=" + buyId;
	ajaxGet(url);
	if(!ajaxSuccess){
		return;
	}
 
	buyDate = lblBuyDate + jsonData.date;
	strHtml = strHtml.replace(/\{buyDate}/g, buyDate);
	
	$('#buyorder').html(strHtml);
	

}

function loadPage(){
	closeAlert();
	$('#more').remove();

	var url = rootUrl + "buyproductAction";
	var mydata={action:'list',orderId:orderId};
	ajaxPost(url,mydata);
	if(!ajaxSuccess){
		return;
	}
	
	var strCusItem = "";
	for(var i=0;i<jsonData.length;i++){
		buyProdId = jsonData[i].buyProdId;
		prodName = jsonData[i].prodName;
		buyPrice= lblBuyPrice + jsonData[i].buyPrice;
		sellPrice= lblSellPrice + jsonData[i].sellPrice;
		category=jsonData[i].category;
		status = jsonData[i].status;
		shop = jsonData[i].shop;
		 
		price = lblCnt + jsonData[i].cnt + " | " + buyPrice + " | " + sellPrice;
		
		prodName = "[" + category + "]" + prodName;
 
		strCusItem = strCusItemSample.replace(/\{buyProdId}/g, buyProdId);
		strCusItem = strCusItem.replace(/\{orderId}/g, orderId);
		strCusItem = strCusItem.replace(/\{prodName}/g, prodName);
		strCusItem = strCusItem.replace(/\{status}/g, status);
		strCusItem = strCusItem.replace(/\{price}/g, price);
		$cusList.append(strCusItem);
		$cusList.append(strLine);
	}
 	
}

function del(buyProdId){
	var url = rootUrl + "buyproductAction?action=del&buyProdId=" + buyProdId;
	ajaxGet(url);
	if(ajaxSuccess){
		location.href="buyproduct.html?orderId="+orderId;
	}
}
 
function edit(){
	var weixin = $('#weixin').val();
	var name = $('#name').val();
	var mobile = $('#mobile').val();
	var address = $('#address').val();
	
	var json = {customerId:customerId,weixin:weixin,name:name,mobile:mobile,address:address};
	var jsonData = JSON.stringify(json);
 
	var mydata = {action:'edit',jsonData:jsonData};
	var url = rootUrl + "buyorderAction";
	ajaxPost(url,mydata);
	if(ajaxSuccess){
		location.href="buyorder.html?buyId=" + buyId;
	}
}           
 