/*Author: Matt Wolf
Date: 5/1/21
Desc: add the event listener to the clock in clock out button
*/

"use strict"



function clockIn(){
	$('#clockinbtn').ajax({
		async: false,
		type: 'GET',
		url: "/clockin",
		success: function(data, status) {
			let btn = document.createElement('button');
			btn.id = "clock_out_btn";
			btn.addEventListener("click", clockOut, false);
			let clockspan = document.getElementById("btn_span");
			clockspan.childNodes = "";
			btnspan.appendChild(btn);
			},
		failure: function(data, status){
			
		}
		});
}

function clockOut(){
	$('#clockoutbtn').ajax({
		async: false,
		type: 'GET',
		url: "/clockout",
		success: function(data, status) {
			let btn = document.createElement('button');
			btn.id = "clock_in_btn";
			btn.addEventListener("click", clockIn, false);
			let clockspan = document.getElementById("btn_span");
			clockspan.childNodes = "";
			btnspan.appendChild(btn);
			},
			failure: function(data, status){
				
			}
		});
}

function init(){
	if ($('#clockinbtn')){
    	$('#clockinbtn').on("click", clockIn());
    } else{
    		$('#clockoutbtn').on("click", clockOut());
    }
}

//eventlisteners for this page
if (window.addEventListener) {
	window.addEventListener("load", init, false);
} else if (window.attachEvent) {
	window.attachEvent("onload",init);
}