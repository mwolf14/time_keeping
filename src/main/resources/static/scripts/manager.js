/*Author: Matt Wolf
Date: 5/14/21
Desc: add the event listener to the clock in clock out button
*/

"use strict"

function approve(){
	$.ajax({
				async: false,
		type: 'GET',
		url: "/approveemployee/id=" + this.id.split('_')[1],
		success: function(data, status) {
			this.onclick = "";
			this.disable= true;
			this.display= none;
			},
			failure: function(data, status){
				console.log(data);
			}
		});	
}

function init(){
	//approve employees
	$(document).on('click', '.approve_employee_btn', function(){
		$.ajax({
					async: false,
			type: 'GET',
			url: "/approveemployee/" + this.id ,
			success: function(data, status) {
				let id = this.id;
				let ids = id.split('_');
				let row = document.getElementById(ids[1] + '_row');
				row.innerHTML = "";
					},
				failure: function(data, status){
					console.log(data);
				}
			});		
	});
	//approve timecards
	$(document).on('click', '.approve_timecard_btn', function(){
		$.ajax({
				async: false,
		type: 'GET',
		url: "/correttimeticket/" + this.id,
		success: function(data, status) {
			let row = document.getElementById(this.id + '_row');
			row.innerHTML = "";
			},
			failure: function(data, status){
				console.log(data);
			}
		});
	})
}

//eventlisteners for this page
if (window.addEventListener) {
	window.addEventListener("load",init, false);
} else if (window.attachEvent) {
    window.attachEvent("onload",init);
}