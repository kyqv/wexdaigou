/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */
var appkey = "be6f012fbf900b20643643ee2714aa0c";
 
var cHkdEx = $.cookie("hkdEx");
if(typeof(cHkdEx) == "undefined"){
	hkdEx = 0.90;  //default value
	$.cookie("hkdEx", hkdEx);
	refreshHkdEx();
} else {
	hkdEx = getFloat(cHkdEx);
	if((hkdEx <= 0) || (hkdEx > 1)){
		hkdEx = 0.90;
		$.cookie("hkdEx", hkdEx);
		refreshHkdEx();
	}
	$.cookie("hkdEx", hkdEx);
}
 

/*
function getHkdEx(){
	url = "http://web.juhe.cn:8080/finance/exchange/rmbquot?key="+appkey;
	$.ajax({
		 url:url,
		 dataType: "jsonp",
		 type: "get",
         async:false,
		 success:function(data){
			 var rList = data.result;
			 for(var i=0;i<rList.length;i++){
				 if(rList[i].name=="港币"){
					 hkdEx = getFloat(rList[i].fBuyPri) / 100;
					 $.cookie("hkdEx", hkdEx);
					 return;
				 }
			 }
		 },
        error:function(xhr, ajaxOptions, thrownError){ 
		},
        timeout: 10000
		});
	
}
*/


function refreshHkdEx(){
	
	var url = rootUrl + "commAction?action=getRmbEx";
	ajaxGet(url);
	if(!ajaxSuccess){
		return;
	}
	
	var errorCode = jsonData.error_code;
    if(errorCode != 0 ){
        showAlert(errorCode + "," + jsonData.reason);
    }
    
    result = jsonData.result[0];
    for (key in result){
		 if(result[key].name=="港币"){
			 hkdEx = getFloat(result[key].fBuyPri) / 100;
			 $.cookie("hkdEx", hkdEx);
			 return;
		 }
    }
}

function getRMBfromHKD(hkd){
	var intHKD = getInt(hkd);
	var rmb = intHKD * hkdEx;
	var intRMB = Math.round(rmb);
	return intRMB;
}