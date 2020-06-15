function   keyLogin() { 
		if(event.keyCode=="13") 
			{   
			addOrUpdateInfo();
			} 
		} 

$(document).ready(function() {
	if (self != top) { 
		
		window.top.location.href="/evaluateSystem/";
		return;
	}
})