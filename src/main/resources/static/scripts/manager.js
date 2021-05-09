/*Author: Matt Wolf
Date: 5/1/21
Desc: add the event listener to the clock in clock out button
*/

"use strict"

function approve(){
	$.ajax({
				async: false,
		type: 'GET',
		url: "/approveemployee/id=" + this.id,
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
			url: "/approveemployee/id=" + this.id ,
			success: function(data, status) {
				this.onclick = "";
				this.text = "Approved";
				this.className = "alreadyApproved"; 
				this.disable= true;
				},
				failure: function(data, status){
					console.log(data);
				}
			});		
	});
	//approve timecards
	$(document).on('click', '.approve_timecard_btn', function(){
		
		debugger;
		
		let ids = this.id.split('_');
		let row = document.getElementById(ids[1])
		let starttime = row[0];
		let endtime= row[1];
			$.ajax({
				async: false,
		type: 'GET',
		url: "/correttimetiekcet/" + this.id,
		success: function(data, status) {
			console.log(data.toString + " " + status);
			this.onclick = "";
			this.disable= true;
			this.display= none;
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