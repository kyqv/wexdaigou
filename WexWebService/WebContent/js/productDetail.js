/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */
var prodId = 0;
var imgData = "";

$(document).ready(function() {
	
	chkLogin();
	
	//get product Id
	var prev = window.location.search.substring(1);
	var prevArray = prev.split('&');
	for(var i=0; i<prevArray.length; i++){
		var indexPair = prevArray[i].split('=');
		if(indexPair[0]=='prodId'){ prodId = getInt(indexPair[1]);}
	}

	if(prodId != 0){
		loadPage();
		loadProdImg();
	} else {
		$('#btnDel').remove();
	}
	

	loadHkdEx();
	
});

function loadPage(){

	var url = rootUrl + "productAction?action=get&prodId=" + prodId;
	ajaxGet(url);
	if(!ajaxSuccess){
		return;
	}
	
	$('#more').remove();
	
	$('#prodName').val(jsonData.prodName);
	$('#buyPrice').val(jsonData.buyPrice);
	$('#sellPrice').val(jsonData.sellPrice);
	$('#category').val(jsonData.category);
	$('#shop').val(jsonData.shop);
 
 	//$('#productImg').attr('src',jsonData.img);
	
}

function del(){
	var url = rootUrl + "productAction?action=del&prodId=" + prodId;
	ajaxGet(url);
	if(ajaxSuccess){
		location.href="product.html";
	}
}

function edit(){
	var prodName = $('#prodName').val();
	var buyPrice = $('#buyPrice').val();
	var sellPrice = $('#sellPrice').val();
	var category = $('#category').val();
	var shop = $('#shop').val();

	
	if((prodName=="") || (buyPrice == "") || (sellPrice == "") || (category=="")){
		showAlert(msg03);
		return;
	}
	
	var json = {prodId:prodId,prodName:prodName,buyPrice:buyPrice,sellPrice:sellPrice,category:category,shop:shop};
	var jsonData = JSON.stringify(json);
 
	var mydata = {action:'edit',jsonData:jsonData,imgData:imgData};
	var url = rootUrl + "productAction";
	ajaxPost(url,mydata);
	if(ajaxSuccess){
		location.href="product.html";
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

			$('#productImg').attr('src',newImageData);
		};
	}
	
});

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

function loadProdImg(){
	var url = rootUrl + "productAction?action=getImg&prodId=" + prodId;
	$.ajax({
       	url: url,
       	type:"GET",
       	dataType:"json",
       	async:true,
       	success: function(msgs){
       		if(msgs.successful){
       			if(typeof(msgs.data) != "undefined"){
       				jsonData = JSON.parse(msgs.data);
       				$('#productImg').attr('src',jsonData.img);
       			}
       		}else{
       			//alert(msgs.code);
       			checkServerError(msgs.errCode,msgs.errMsg);
       		}
        },
        error:function(xhr, ajaxOptions, thrownError){
       		//alert(thrownError);
           	if(thrownError == "timeout"){
				showAlert("Network error!");
           	}else{
           		checkServerError("System error",'Time out!', '');	
           	}
       	},
       	timeout: 8000
	});
}
           
 