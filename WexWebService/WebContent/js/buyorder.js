/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */
var buyId = 0;
var start = 0;
var searchTxt = $('#searchTxt').val();

var strCusItemSample = " <li class='item' id='cus-item'>" + $('#cus-item').html() + "</li>";
 
var strLine = "<li class='line'></li>";
var strMore = "<li class='more' id='more'>" + $('#more').html() + "</li>";
var $cusList = $('#cusList');


$(document).ready(function() {
	chkLogin();
 	
	//get buy Id
	var prev = window.location.search.substring(1);
	var prevArray = prev.split('&');
	for(var i=0; i<prevArray.length; i++){
		var indexPair = prevArray[i].split('=');
		if(indexPair[0]=='buyId'){ buyId = getInt(indexPair[1]);}
	}
	
	$cusList.html("");
	
	if(buyId != 0){
		loadBuy();
		loadPage();
	} else {
		location.href="index.html";
	}
 
});


function loadBuy(){
	var url = rootUrl + "buylistAction?action=get&buyId=" + buyId;
	ajaxGet(url);
	if(!ajaxSuccess){
		return;
	}
	
	strHtml = $('#buylist').html();
	
	buyId = jsonData.buyId;
	buyDate = lblBuyDate + jsonData.date;
	month = jsonData.month;
	year = jsonData.year;
	status = jsonData.status;
	
	buyPrice= lblBuyPrice + jsonData.buyPrice;
	sellPrice= lblSellPrice + jsonData.sellPrice;
	disCnt = lblCnt + jsonData.cnt;
	
	price = disCnt + " | " + buyPrice + " | " + sellPrice;
	//prodName = category + " : " + prodName;

	strHtml = strHtml.replace(/\{buyId}/g, buyId);
	strHtml = strHtml.replace(/\{buyDate}/g, buyDate);
	strHtml = strHtml.replace(/\{price}/g, price);
	strHtml = strHtml.replace(/\{status}/g, status);
	
	$('#buylist').html(strHtml);
	
	url = "editbuyorder.html?buyId=" + buyId;
	$('#hrefnewOrder').attr('href',url);

}

function loadPage(){
	closeAlert();
	$('#more').remove();
	
	var url = rootUrl + "buyorderAction";
	cnt = 99;
	var mydata={action:'list',buyId:buyId,start:start,cnt:cnt,searchTxt:searchTxt};
	ajaxPost(url,mydata);

	if(!ajaxSuccess){
		return;
	}
	
	
	var strCusItem = "";
	for(var i=0;i<jsonData.length;i++){

		orderId = jsonData[i].orderId;
		name = jsonData[i].name;
		address=jsonData[i].address;
		weixin=jsonData[i].weixin;
		mobile=jsonData[i].mobile;
		
		status = jsonData[i].status;
		
		buyPrice= lblBuyPrice + jsonData[i].buyPrice;
		sellPrice= lblSellPrice + jsonData[i].sellPrice;
		disCnt = lblCnt + jsonData[i].cnt;
		price = disCnt + " | " + buyPrice + " | " + sellPrice;
		
		customerName = name + " (" + weixin + ")";
		
		strCusItem = strCusItemSample.replace(/\{orderId}/g, orderId);
		strCusItem = strCusItem.replace(/\{customerName}/g, customerName);
		strCusItem = strCusItem.replace(/\{mobile}/g, mobile);
		strCusItem = strCusItem.replace(/\{address}/g, address);
		strCusItem = strCusItem.replace(/\{price}/g, price);
		strCusItem = strCusItem.replace(/\{status}/g, status);
		
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

function del(orderId){
	var url = rootUrl + "buyorderAction?action=del&orderId=" + orderId;
	ajaxGet(url);
	if(ajaxSuccess){
		location.href="buyorder.html?buyId="+buyId;
	}
}
           
 