

function init(){
	let clockInButton = document.getElementById("clockInButton");
	clockInButton.onclick =  function(){
	$.ajax({
		async: false,
		type: 'Get',
		url: "/api/clockin",
		success: function(data, status) {
			clockInButton.disabled = true;
			},
		error: function(){
			console.log("there has been an error");
		}
});
	};
}


if (window.addEventListener) {
    window.addEventListener("load", init, false);
    
} else if (window.attachEvent) {
    window.attachEvent("onload", init);
    }