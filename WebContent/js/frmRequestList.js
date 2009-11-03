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
	//alert("Got from ajax:"+p);
	document.getElementById("searchdiv").innerHTML = p;
}



var selectedIdx = -1;

function cleanUp() {
	var arobj = document.getElementById("searchdiv").getElementsByTagName("TR");

	for (var i =1 ; i < arobj.length; i++) {
		arobj[i].style.backgroundColor= "#d0d0a0";
	}
}


document.onclick = function(e){
	var targ;
	selectedIdx = -1;
	cleanUp();

	if (!e)
	{
		var e=window.event;
	}
	if (e.target)
	{
		targ=e.target;
	}
	else if (e.srcElement)
	{
		targ=e.srcElement;
	}
	if (targ.nodeType==3) // defeat Safari bug
	{
		targ = targ.parentNode;
	}
	var tname;
	tname=targ.tagName;
	var obj  = targ;
	if(obj.tagName)
		while( obj  != null && obj.tagName != "TR" && obj.tagName != "DIV"   ){
			obj = obj.parentElement;
		}

	if(obj != null && (obj.tagName == "tr" || obj.tagName == "TR" ) && obj.childNodes[0].tagName != "TH"  ){
		selectedIdx  = obj.rowIndex;
		obj.style.backgroundColor= "#a0b0a0";
	}

}


//create url with where clause
function makeWhereClause(){
	//alert("in make url");
	//There will be only one table in search screen 'search div'
	listTable = document.getElementById("searchdiv").getElementsByTagName("table")[0];
	
	whereClause = "&panelFields1WhereClause=";
		
	//poplate wher clause url
	for (i = 0; i <listTable.rows[0].cells.length ; i++ )
	{
		//alert(listTable.rows[0].cells[i].childNodes[0].innerText.split(',')[6]);
		if(listTable.rows[0].cells[i].childNodes[0].innerText.split(',')[6]  == "Y") {
			name = listTable.rows[0].cells[i].innerText.split(',')[2];	 
			value = listTable.rows[selectedIdx].cells[i].innerText;
			whereClause = whereClause + name + "!" + value + "~#";
		}
	}
	whereClause = whereClause.replace(/(~#)$/, '');
	
	document.getElementById("panelFieldsWhereClause").value=whereClause;
	
	//alert(whereClause);
	
}