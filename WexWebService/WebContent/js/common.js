/* 
 * Date: 2016-12-29
 * Copyright (C) tracemouse. All rights reserved.
 * This software is Made by tracemouse(tracemouse@163.com).
 */
var thisUrl = String(window.location);

var strArr = window.location.href.split('/');
var thisPage = strArr[strArr.length - 1].split('?')[0];
thisPage = (thisPage.charAt(thisPage.length - 1) == '#')?thisPage.substr(0,	thisPage.length - 1):thisPage;


var prevUrl = $.cookie("currentUrl");
prevUrl = (typeof(prevUrl) == "undefined")?"":prevUrl;
if(thisPage != "login.html"){
	$.cookie("currentUrl", thisUrl);
}
 
var rootUrl = "";
//var rootUrl = "http://192.168.1.101:8080/WexWebService/"



var pageBtn = 6;  // No of button in page nav bar, can be override by page's script(e.g. useradmin.js)
var cnt = 10;
var maxImg = 650;

//json object for ajax
var jsonData = {};
var ajaxSuccess = false;

// Set timeout after 6 minutes
setTimeout(function (){location.href="login.html";},6000000);

// import alert dialog
//$("body").append( getHtmlFromFile("include/dialog.html"));
 
 