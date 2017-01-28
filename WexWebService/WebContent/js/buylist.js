/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */
var buyId = 0;
var buyDate = "";

var $itemList = $('#itemList');
var strCategory = '<div id="category" class="one main" style="padding-left:10px;padding-right:15px;">' + $('#category').html() + '</div>';
var strProduct = '<a href="selectproduct.html?orderId={orderId}&buyProdId={buyProdId}"><div id="product" class="two main buylist-item bottom-dashed " style="padding-left:10px;padding-right:15px;">' + $('#product').html() + '</div></a>';
var strHeader = '<div id="header" class="two main" style="padding-left:10px;padding-right:15px;">' + $('#header').html() + '</div>';
var strAction = '<div id="header" class="one remark buylist-action" style="padding-left:10px;padding-right:15px;">' + $('#action').html() + '</div>';
var strLine = '<div id="vline" class="one buylist-line"></div>';


$(document).ready(function() {
	chkLogin();
	
	//get buy Id
	var prev = window.location.search.substring(1);
	var prevArray = prev.split('&');
	for(var i=0; i<prevArray.length; i++){
		var indexPair = prevArray[i].split('=');
		if(indexPair[0]=='buyId'){ buyId = getInt(indexPair[1]);}
	}
		
	if(buyId != 0){
		loadBuy();
		$('#itemList').html("");
		loadPage();
	} else {
		location.href="index.html";
	}
	
	//refreshHkdEx();
 	
	loadHkdEx();
});


function loadBuy(){
	var url = rootUrl + "buylistAction?action=get&buyId=" + buyId;
	ajaxGet(url);
	if(!ajaxSuccess){
		return;
	}
	
	strHtml = $('#buylist').html();
	
	buyId = jsonData.buyId;
	disDate = lblBuyDate + jsonData.date;
	buyDate = jsonData.date;
	month = jsonData.month;
	year = jsonData.year;
	status = jsonData.status;
	
	buyPrice= lblBuyPrice + jsonData.buyPrice;
	sellPrice= lblSellPrice + jsonData.sellPrice;
	disCnt = lblCnt + jsonData.cnt;
	
	//price = disCnt + " | " + buyPrice + " | " + sellPrice;
	price = jsonData.cnt + " | " + lblYuan + jsonData.buyPrice + " | " + lblYuan + jsonData.sellPrice;
	
	//prodName = category + " : " + prodName;

	//strHtml = strHtml.replace(/\{buyId}/g, buyId);
	strHtml = strHtml.replace(/\{buyDate}/g, disDate);
	strHtml = strHtml.replace(/\{price}/g, price);
	//strHtml = strHtml.replace(/\{status}/g, status);
	
	$('#buylist').html(strHtml);
	
	url = "editbuyorder.html?buyId=" + buyId;
	$('#hrefnewOrder').attr('href',url);

}


function loadPage(){
	closeAlert();
	
	var url = rootUrl + "buyproductAction";
	
	var mydata={action:'listall',buyId:buyId};
		
	ajaxPost(url,mydata);
	if(!ajaxSuccess){
		return;
	}
	
	if(jsonData.length == 0){
		showAlert(msg00);
	}

	var prevCategory = "";
	var strHtml = "";
	var disRow = 0;
	
	for(var i=0;i<jsonData.length;i++){
		
		buyProdId = jsonData[i].buyProdId;
		orderId = jsonData[i].orderId;
		prodName = jsonData[i].prodName;
		//buyPrice= lblBuyPrice + jsonData[i].buyPrice;
		buyPrice = jsonData[i].buyPrice;
		sellPrice= lblSellPrice + jsonData[i].sellPrice;
		category=jsonData[i].category;
		status = jsonData[i].status; 
		//price = lblCnt + jsonData[i].cnt + " | " + buyPrice + " | " + sellPrice;
		disCnt = jsonData[i].cnt;
		shop = jsonData[i].shop;
	
		if(category != prevCategory) {
			//strHtml += strCategory.replace(/\{category}/g, category);
			prevCategory = category;
			disRow = 0;
		}

		//disRow = disRow + 1;
		//prodName = disRow + ". " + prodName;
		prodName = (shop != "")?"<b>[" + shop + "]</b>" + prodName:prodName; 
		
		strItem = strProduct.replace(/\{prodName}/g, prodName) + strAction.replace(/\{buyPrice}/g, buyPrice);
		strItem = strItem.replace(/\{cnt}/g, disCnt);
		strItem = strItem.replace(/\{buyProdId}/g, buyProdId);
		strItem = strItem.replace(/\{orderId}/g, orderId);	
		
		strHtml += strItem + strLine; 
		
		$itemList.html(strHeader +  strHtml);
	}
	
	
}

function savePic(){
	  html2canvas($("#myBuylist"), {
		     background:'#F0F0F0',
		     //width:1000,
	         onrendered: function (canvas) {
	             var url = canvas.toDataURL();
	              //以下代码为下载此图片功能
	             var triggerDownload = $("<a>").attr("href", url).attr("download", buyDate + "采购清单.png").appendTo("body");
	               triggerDownload[0].click();
	               triggerDownload.remove();
	           }
	   });
}
 
function chgBuyPrice(buyProdId){
	var buyPrice = $('#buyPrice-' + buyProdId).val();
	var rmbPrice = getRMBfromHKD(buyPrice);

	$('#buyPrice-' + buyProdId).val(rmbPrice);
	
}

function updateHkdEx(){
	refreshHkdEx();
	loadHkdEx();
}

function loadHkdEx(){
	var disHkdEx = (hkdEx + "").substr(0,4);
	$('#hkdEx').html(disHkdEx);
}

function buy(buyProdId){
	var buyPrice = $('#buyPrice-' + buyProdId).val();
	var status = "已采购";
 
	var json = {buyProdId:buyProdId,buyPrice:buyPrice,status:status};
	var jsonData = JSON.stringify(json);
	
 	var mydata = {action:'buy',jsonData:jsonData};
	var url = rootUrl + "buyproductAction";
	ajaxPost(url,mydata);
	if(ajaxSuccess){
		location.href = thisUrl;
	}
}

function buyFail(buyProdId){
	var buyPrice = $('#buyPrice-' + buyProdId).val();
	var status = "缺货";
 
	var json = {buyProdId:buyProdId,buyPrice:buyPrice,status:status};
	var jsonData = JSON.stringify(json);
	
 	var mydata = {action:'buy',jsonData:jsonData};
	var url = rootUrl + "buyproductAction";
	ajaxPost(url,mydata);
	if(ajaxSuccess){
		location.href = thisUrl;
	}
}