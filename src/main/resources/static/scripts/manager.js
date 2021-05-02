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
			},
			failure: function(data, status){
				console.log(data);
			}
		});	
}

function init(){
	$(document).on('click', '.approve_employee_btn', function(){
		$.ajax({
					async: false,
			type: 'GET',
			url: "/approveemployee/" + this.id,
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
}

//eventlisteners for this page
if (window.addEventListener) {
	window.addEventListener("load",init, false);
} else if (window.attachEvent) {
    window.attachEvent("onload",init);
}