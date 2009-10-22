function search(){
	var url=urlpart+"?panelName=searchPanel&screenName="+screenName;
	if(document.getElementById("sempid"))
	url=url+'&sempid='+document.getElementById("sempid").value;
	if(document.getElementById("sempname"))
		url=url+'&sempname='+document.getElementById("sempname").value;
	alert(url);	
	sendAjaxGet(url,mycall);
}
function mycall(p){
	 alert("Got from ajax:"+p);
	 document.getElementById("searchdiv").innerHTML = p;
}