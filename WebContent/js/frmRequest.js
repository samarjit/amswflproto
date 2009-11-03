function populate()
{
	alert("This alert box was called with the onload event");	
	var url=urlpart+"?panelName=searchPanel&screenName="+screenName;	
	url=url+ whereClause;	
	alert("In message" + whereClause);
	prompt("url",url);	
	sendAjaxGet(url, requestCallBack);
}

function requestCallBack(p){
	//alert("Got from ajax:"+p);

	document.getElementById("searchdiv").innerHTML = p;	
	panelsTable = document.getElementById("panelsdiv").getElementsByTagName("table");
	//alert(panelsTable.length);
	detailTable    = document.getElementById("searchdiv").getElementsByTagName("table");

	for ( var i=0; i<detailTable.length ; i++)
	{
		//alert(detailTable[i].id);			
		if (detailTable[i].id == 'buttonPanel')
			continue;
		for(var k = 0; k<detailTable[i].rows[0].cells.length; k++) {			
			comStr = detailTable[i].rows[0].cells[k].childNodes[0].innerText.split(',')[2];	 			
			//alert(comStr);
			//alert(detailTable[i].rows[0].cells[k].innerText);
			//comVal = detailTable[i].rows[1].cells[k].data;	  
			comVal = detailTable[i].rows[1].cells[k].innerText;	  
			//alert(comVal);				
			for(var l = 0; l<panelsTable.length; l++)
			{
				//alert(panelsTable[i].id);
				if (panelsTable[l].id == 'buttonPanel')
					continue;
				if(detailTable[i].id == panelsTable[l].id)
				{
					var input = panelsTable[l].getElementsByTagName("input");
					//alert(input.length);

					for( var m = 0 ; m < input.length; m++)
					{
						if(input[m].id == comStr)
						{
							//alert(comStr +"   "+comVal +"   " +panelsTable[l].id + " " + detailTable[i].id);
							input[m].value = comVal;
						}
					}
				}
			}
		}
	}

}