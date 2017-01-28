/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */
$(document).ready(function() {

	chkLogin();
    
	var url = rootUrl + "dashboardAction";
	var mydata={action:'get'};
	ajaxPost(url,mydata);
	if(!ajaxSuccess){
		return;
	}
	
	$('#buylistCnt').html(jsonData.buylistCnt);
	$('#buyorderCnt').html(jsonData.orderCnt);
	$('#productCnt').html(jsonData.productCnt);
	$('#customerCnt').html(jsonData.customerCnt);
	
});

 