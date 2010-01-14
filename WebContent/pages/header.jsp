<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<div id="logo">    
<h1>Asset Management System</h1>
<script>
function fnCreateActivity(ac) { 
	var ctxpath = '<%=request.getContextPath() %>';
	var url=ctxpath+'/workflow.action?';
	if(ac== 'CR'){
	url+="activityname=CR&create=true";
	}else if(ac=='RFQ'){
		
	}
	//alert(url);
//document.getElementById("frmmenu").action = url;
location.href=url;
}
</script>

</div>
<hr/>
<div id="header">
<div id="menu">

<ul>
<li><a href="template1.action?screenName=frmRequestList">Request</a></li>
<li><a href="#">RFQ</a> </li>
<li><a href="#">Quotation</a></li>
<li><a href="logout.action">Logout</a></li>
</ul>
<br/>
</div>
</div>
<br/>
<jsp:include page="/pages/wflproto.jsp"></jsp:include>


<div style="display:block">
<jsp:include page="/pages/actionbutton.jsp"></jsp:include>
</div>