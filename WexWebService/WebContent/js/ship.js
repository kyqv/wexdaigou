/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */
var orderId=0;
var buyId=0;
var imgData = "";
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
	var url = rootUrl + "buyorderAction?action=getShip&orderId=" + orderId;
	ajaxGet(url);
	if(!ajaxSuccess){
		return;
	}
	
	ship = jsonData.ship;
	shipNo = jsonData.shipNo;
	imgData = jsonData.shipImg;
	
	$('#ship').val(ship);
	$('#shipNo').val(shipNo);
 	$('#shipImg').attr('src',imgData);
	
}


function edit(){
	var status = $('#status').val();
	var ship = $('#ship').val();
	var shipNo = $('#shipNo').val();
	
	var json = {orderId:orderId,status:status,ship:ship,shipNo:shipNo,shipImg:imgData};
	var jsonData = JSON.stringify(json);
 
	var mydata = {action:'ship',jsonData:jsonData};
	var url = rootUrl + "buyorderAction";
	ajaxPost(url,mydata);
	if(ajaxSuccess){
		location.href="buyorder.html?buyId=" + buyId;
	}
}         

$('#img').change(function(){
	var _this = $(this)[0];
	_file = _this.files[0];
	fileType = _file.type;
	if(!(fileType == "image/jpeg" || fileType == "image/png")){
		showAlert("图片格式不正确,只支持jpeg,png格式图片");
		return;
	}
	var fileReader = new FileReader();
	fileReader.readAsDataURL(_file);
	fileReader.onload = function(event){
		var result = event.target.result;
		var image = new Image();
		image.src = result;

		image.onload = function(){
			var cvs = document.createElement('canvas');
			var scale = 1;
 
			if(this.width > maxImg || this.height > maxImg){
				if(this.width > this.height) {
					scale = maxImg / this.width;
				}else{
					scale = maxImg / this.height;
				}
			}
			cvs.width = this.width * scale;
			cvs.height = this.height * scale;
 
			var ctx = cvs.getContext("2d");
			ctx.drawImage(this,0,0,cvs.width,cvs.height);
			var newImageData = cvs.toDataURL(fileType,0.8);
			var sendData = newImageData.replace("data:"+fileType+";base64,","");
			imgData = newImageData;

			$('#shipImg').attr('src',newImageData);
		};
	}
	
});

 
 