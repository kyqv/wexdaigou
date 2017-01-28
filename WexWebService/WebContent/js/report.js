/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */
var action="topYear";
var orderBy="gain desc ";
var category="";

var strCusItemSample = " <li class='head' id='cus-item'>" + $('#cus-item').html() + "</li>";
var strLine = "<li class='line'></li>";
var strMore = "<li class='more' id='more'>" + $('#more').html() + "</li>";
var $cusList = $('#cusList');


$(document).ready(function() {
	chkLogin();

	var prev = window.location.search.substring(1);
	var prevArray = prev.split('&');
	for(var i=0; i<prevArray.length; i++){
		var indexPair = prevArray[i].split('=');
		if(indexPair[0]=='category'){ category = decodeURIComponent(indexPair[1]);}	
	}	
 
	if(category != ""){
		$('#btnCat1').attr('class','');
		$('#btnCat1').removeAttr('disabled');
		$('#btnCat2').attr('class','wallpaper-hover');
		$('#btnCat2').removeAttr('href');	
		$('#btnCat2').html(category);
		for(var i=1;i<=3;i++){
			id="#hrefCat" + i;
			href = $(id).attr('href') + "?category=" + category;
			$(id).attr('href',href);
		}
	}
	
	
	$cusList.html("");

	loadPage();
});


function loadPage(){
	closeAlert();
	$('#more').remove();

	var url = rootUrl + "reportAction";
	var mydata={action:action,orderBy:orderBy,category:category};
	ajaxPost(url,mydata);
	if(!ajaxSuccess){
		return;
	}
 
	var strCusItem = "";
	for(var i=0;i<jsonData.length;i++){
 
		time = jsonData[i].time;
		
		buyPrice= lblYuan + jsonData[i].buyPrice;
		sellPrice= lblYuan + jsonData[i].sellPrice;
		gain = lblYuan + jsonData[i].gain;
		productCnt = jsonData[i].productCnt;
		//price = buyPrice + " | " + sellPrice + " | " + gain; 
		
		strCusItem = strCusItemSample.replace(/\{time}/g, time);
		strCusItem = strCusItem.replace(/\{sellPrice}/g, sellPrice);
		strCusItem = strCusItem.replace(/\{buyPrice}/g, buyPrice);
		strCusItem = strCusItem.replace(/\{gain}/g, gain);
		//strCusItem = strCusItem.replace(/\{price}/g, price);
		strCusItem = strCusItem.replace(/\{productCnt}/g, productCnt);
 
		$cusList.append(strCusItem);
		$cusList.append(strLine);
	}
	
	if(jsonData.length == cnt) {
		$cusList.append(strMore);
	}
	
	//start = start + cnt;
	
}


function reOrder(order,id){
 
	var tot = 4;
	for(var i = 1; i <= tot; i++){
		btnId = '#btnOrder' + i;
 
		if(i == id){
			//$(btnId).attr('css','');
			$(btnId).attr('disabled','disabled');
		}else{
			//$(btnId).attr('css','btn btn-default');
			$(btnId).removeAttr('disabled');
		}
	}
 
 
	
	orderBy = order;
	$cusList.html("");
	loadPage();
}

 
 
 