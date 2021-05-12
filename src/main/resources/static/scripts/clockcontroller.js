/*Author: Matt Wolf
Date: 5/10/21
Desc: add the event listener to the clock in clock out button
*/
"use strict"
function clockIn(){
	$.ajax({
		async: false,
		type: 'GET',
		url: "/clockin",
		success: function(data, status) {
			let btn = document.createElement('button');
			btn.id = "clockoutbtn";
			btn.addEventListener("click", clockOut, false);
			let clockspan = document.getElementById("btn_span");
			clockspan.innerHTML = "";
			btnspan.appendChild(btn);
			},
		failure: function(data, status){
			
		}
		});
}

function clockOut(){
	$.ajax({
		async: false,
		type: 'GET',
		url: "/clockout",
		success: function(data, status) {
			let btn = document.createElement('button');
			btn.id = "clockinbtn";
			btn.addEventListener("click", clockIn, false);
			let clockspan = document.getElementById("btn_span");
			clockspan.innerHTML = "";
			btnspan.appendChild(btn);
			},
			failure: function(data, status){
				
			}
		});
}

function init(){
	console.log("in the init function of the clockcontroller script");
	if ($('#clockinbtn')){
		console.log("in the init function clockinbtn");
    	$('#clockinbtn').on("click", clockIn());
    } else{
			console.log("in the init function clockoutbtn");
    		$('#clockoutbtn').on("click", clockOut());
    }
}

//eventlisteners for this page
if (window.addEventListener) {
	console.log("in addEventListener");
	window.addEventListener("load",init, false);
} else if (window.attachEvent) {
	console.log("in attachEvent");
	window.attachEvent("onload",init);
}