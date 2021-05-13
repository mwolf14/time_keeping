/*Author: Matt Wolf
Date: 5/10/21
Desc: add the event listener to the clock in clock out button
*/
"use strict"
//the next 2 functions will probably need deleted. moved it to an anon in init
function clockIn(){
	$('#clockinbtn').on("click", function(){
			$.ajax({
		async: false,
		type: 'GET',
		url: "/clockin",
		success: function(data, status) {
			let btn = document.createElement('button');
			btn.id = "clockoutbtn";
			btn.textContent = "Clock Out";
			btn.addEventListener("click", clockOut, false);
			let btn_span = document.getElementById("btn_span");
			btn_span.innerHTML = "";
			btn_span.appendChild(btn);
			},
		failure: function(data, status){
			
		}
	})});
}

function clockOut(){
	$('#clockoutbtn').on("click", function(){
					$.ajax({
		async: false,
		type: 'GET',
		url: "/clockout",
		success: function(data, status) {
			let btn = document.createElement('button');
			btn.id = "clockinbtn";
			btn.textContent = "Clock In";
			btn.addEventListener("click", clockIn, false);
			let btn_span = document.getElementById("btn_span");
			btn_span.innerHTML = "";
			btn_span.appendChild(btn);
			},
			failure: function(data, status){
				
			}
			});
		});
}

function init(){
	console.log("in the init function of the clockcontroller script");
	if ($('#clockinbtn')){
		console.log("in the init function clockinbtn");
    	clockIn();
		
    } else{
			console.log("in the init function clockoutbtn");
    		clockOut();
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