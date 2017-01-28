/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */
// read html file 
function getHtmlFromFile(file){
	var strHtml = "";
	$.ajax({
           	url: file,
           	dataType: "text",
			async:false,   //next sentence will run after ajax end
           	success: function(data){
				strHtml = data;
            },
            error:function(xhr, ajaxOptions, thrownError){ 
				alert(thrownError +"Fail to read server file '" + file + "', please contact ITD.");
           	},
           	timeout: 5000
   	});
 
	return strHtml;
}

//show confirmation dialog
function showConfirm(msg, action){
	//set action for the button 'Yes'
	action = "$('#wexDgConfirm').modal('hide');" + action;
	$("#wexDgConfirm #btnYes").attr("onclick",action);
	//set message text
	$("#wexDgConfirm .modal-body").html(msg);
	//show confirm dailog
	$("#wexDgConfirm").modal({backdrop:false,keyboard:true,show:true});
}

//show alert dialog
function showAlert(msg, action){
	/*
	//set action for the button 'Yes'
	action = "$('#wexDgAlert').modal('hide');" + action;
	$("#wexDgAlert #btnOk").attr("onclick",action);
	//set message text
	$("#wexDgAlert .modal-body").html(msg);
	//show confirm dailog
	$("#wexDgAlert").modal({backdrop:false,keyboard:true,show:true});
	*/
	$('#alertMsg').html(msg);
	//$('#alertDg').alert('close');
	$('#alertDg').show();
	
	//$('#alertDg').css('display','block');
}

function closeAlert(){
	$('#alertMsg').html("");
	$('#alertDg').hide();
}

//Check error code returned from server
function checkServerError(errorCode, errorMsg, action){
	if (errorCode == "ERR_INVALID_SESSION"){
		location.href = "login.html";
	}else{
		//alert(errorCode + ": " + errorMsg);
		showAlert(errorCode + ": " + errorMsg, action);
	}
}

//ajax function
function ajaxPost(url, mydata){
	ajaxSuccess = false;

	$.ajax({
           	url: url,
           	data: mydata,
           	type:"POST",
           	dataType:"json",
           	async:false,
           	success: function(msgs){
           		if(msgs.successful){
           			ajaxSuccess = true;
           			if(typeof(msgs.data) != "undefined"){
           				jsonData = JSON.parse(msgs.data);
           			}
           		}else{
           			checkServerError(msgs.errCode,msgs.errMsg);
           		}
            },
            error:function(xhr, ajaxOptions, thrownError){ 
               	if(thrownError == "timeout"){
					showAlert("Network error!");
	           	}else{
	           		checkServerError("System error",'Time out!', '');
               	}
           	},
           	timeout: 8000
   	});

}

//ajax function
function ajaxGet(url){
	ajaxSuccess = false;
	$.ajax({
           	url: url,
           	type:"GET",
           	dataType:"json",
           	async:false,
           	success: function(msgs){
           		if(msgs.successful){
           			ajaxSuccess = true;
           			if(typeof(msgs.data) != "undefined"){
           				jsonData = JSON.parse(msgs.data);
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
//format date
function formatDate(oDate, sFormat){
	sFormat = sFormat.replace("YYYY", oDate.getFullYear());
	sFormat = sFormat.replace("YY", oDate.getYear());
	sFormat = sFormat.replace("MM", oDate.getMonth()+1);
	sFormat = sFormat.replace("DD", oDate.getDate());
	sFormat = sFormat.replace("hh", oDate.getHours());
	sFormat = sFormat.replace("mm", oDate.getMinutes());
	sFormat = sFormat.replace("ss", oDate.getSeconds());

	sFormat = sFormat.replace(/\b(\d)\b/g, '0$1');

	return sFormat;

}

//convert string to float
function getFloat(str){
	var floatVal = (isNaN(parseFloat(str)))?0:parseFloat(str);
	return floatVal;
}

//convert string to int
function getInt(str){
	var intVal = (isNaN(parseInt(str)))?0:parseInt(str);
	return intVal;
}


function logout(){
	var url = "loginAction?action=logout";
	ajaxGet(url);

	location.href="login.html";
	
}

function chkLogin(){
	var url = rootUrl + "loginAction?action=chklogin";

	ajaxGet(url);
	if(!ajaxSuccess) {
		//alert('Invalid session');
		location.href="login.html";
	}
 
}
