/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */
var buyId=0;
var orderId=0;
var buyProdId=0;

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
		if(indexPair[0]=='buyProdId'){ buyProdId = getInt(indexPair[1]);}
		
	}
	
	$cusList.html("");
		
	if(orderId != 0){
		loadCustomer();
		loadPage();
	} else {
		location.href="index.html";
	}
	
	if(buyProdId !=0){
		loadProduct();
	}
	
	href = "buyorder.html?buyId=" + buyId;
	$('#hrefOrder').attr("href",href);
	href = "buyproduct.html?orderId=" + orderId;
	$('#hrefProduct').attr('href', href);	
	
	loadHkdEx();
	
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

function loadProduct(){
	var url = rootUrl + "buyproductAction?action=get&buyProdId=" + buyProdId;
	ajaxGet(url);
	if(!ajaxSuccess){
		return;
	}
	
	$('#prodName').val(jsonData.prodName);
	$('#buyPrice').val(jsonData.buyPrice);
	$('#sellPrice').val(jsonData.sellPrice);
	$('#category').val(jsonData.category);
	$('#cnt').val(jsonData.cnt);
	$('#shop').val(jsonData.shop);
	$('#status').val(jsonData.status);
}


function loadPage(){
	closeAlert();
	$('#more').remove();

	var url = rootUrl + "productAction";
	var mydata={action:'list',start:start,cnt:cnt,searchTxt:searchTxt};
	ajaxPost(url,mydata);
	if(!ajaxSuccess){
		return;
	}
 
	
	var strCusItem = "";
	for(var i=0;i<jsonData.length;i++){
		prodId = jsonData[i].prodId;
		prodName = jsonData[i].prodName;
		buyPrice= lblBuyPrice + jsonData[i].buyPrice;
		sellPrice= lblSellPrice + jsonData[i].sellPrice;
		category=jsonData[i].category;
		price = buyPrice + " | " + sellPrice;
		shop = jsonData[i].shop;
		//prodName = category + " : " + prodName;
		if(shop != "")	category = category + " | " + shop;
		
		strCusItem = strCusItemSample.replace(/\{prodId}/g, prodId);
		strCusItem = strCusItem.replace(/\{prodName}/g, prodName);
		strCusItem = strCusItem.replace(/\{category}/g, category);
		strCusItem = strCusItem.replace(/\{price}/g, price);
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

function selectproduct(prodId){
	var url = rootUrl + "productAction?action=get&prodId=" + prodId;
	ajaxGet(url);
	if(!ajaxSuccess){
		return;
	}
	
	$('#prodName').val(jsonData.prodName);
	$('#buyPrice').val(jsonData.buyPrice);
	$('#sellPrice').val(jsonData.sellPrice);
	$('#category').val(jsonData.category);
	$('#shop').val(jsonData.shop);
	var i = getInt($('#cnt').val());
	i = i + 1;
	//$('#cnt').val(i);
}

function del(){
	var url = rootUrl + "buyproductAction?action=del&buyProdId=" + buyProdId;
	ajaxGet(url);
	if(ajaxSuccess){
		location.href="buyproduct.html?orderId=" + orderId;
	}
}

function edit(){
	var prodName = $('#prodName').val();
	var buyPrice = $('#buyPrice').val();
	var sellPrice = $('#sellPrice').val();
	var category = $('#category').val();
	var mycnt =  $('#cnt').val();
	var status = $('#status').val();
	var shop = $('#shop').val();
	
	if((prodName=="") || (mycnt == "")) {
		showAlert(msg03);
		return;
	}
	
	var json = {buyProdId:buyProdId,orderId:orderId,buyId:buyId,prodName:prodName,buyPrice:buyPrice,sellPrice:sellPrice,category:category,cnt:mycnt,status:status,shop:shop};
	var jsonData = JSON.stringify(json);
 
	var mydata = {action:'edit',jsonData:jsonData};
	var url = rootUrl + "buyproductAction";
	ajaxPost(url,mydata);
	if(ajaxSuccess){
		location.href="buyproduct.html?orderId=" + orderId;
	}
}

function updateHkdEx(){
	refreshHkdEx();
	loadHkdEx();
}

function loadHkdEx(){
	var disHkdEx = (hkdEx + "").substr(0,4);
	$('#hkdEx').html(lblHkdEx + disHkdEx);
}

function chgBuyPrice(){
	var buyPrice = $('#buyPrice').val();
	var rmbPrice = getRMBfromHKD(buyPrice);
	$('#buyPrice').val(rmbPrice);
	
}
 