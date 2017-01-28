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
	
	var url = rootUrl + "buylistAction";
	var mydata={action:'list',start:start,cnt:cnt,searchTxt:searchTxt};
	ajaxPost(url,mydata);
	if(!ajaxSuccess){
		return;
	}
	
	if(jsonData.length == 0){
		showAlert(msg00);
	}
	
	var strCusItem = "";
	for(var i=0;i<jsonData.length;i++){
		buyId = jsonData[i].buyId;
		date = lblBuyDate + jsonData[i].date;
		month = jsonData[i].month;
		year = jsonData[i].year;
		status = jsonData[i].status;
		
		buyPrice= lblBuyPrice + jsonData[i].buyPrice;
		sellPrice= lblSellPrice + jsonData[i].sellPrice;
		disCnt = lblCnt + jsonData[i].cnt;
		
		price = disCnt + " | " + buyPrice + " | " + sellPrice;
		//prodName = category + " : " + prodName;
 
		strCusItem = strCusItemSample.replace(/\{buyId}/g, buyId);
		strCusItem = strCusItem.replace(/\{buyDate}/g, date);
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
 
function del(buyId){
	var url = rootUrl + "buylistAction?action=del&buyId=" + buyId;
	ajaxGet(url);
	if(ajaxSuccess){
		location.href="buy.html";
	}
}
 