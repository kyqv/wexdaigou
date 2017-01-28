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

function del(prodId){
	var url = rootUrl + "productAction?action=del&prodId=" + prodId;
	ajaxGet(url);
	if(ajaxSuccess){
		location.href="product.html";
	}
}
           
 