<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<h1>Asset Management System</h1>
<script>
function fnsubmit(ac) {alert(ac);
	var ctxpath = '<%=request.getContextPath() %>';
	var url=ctxpath+'/WorkflowController.do?';
	if(ac== 'CR'){
	url+="activityname=CR&create=true";
	}else if(ac=='RFQ'){
		
	}
	//alert(url);
//document.getElementById("frmmenu").action = url;
location.href=url;
}
</script>
<hr/>
<jsp:include page="/pages/wflproto.jsp"></jsp:include>
Menu:
Request|RFQ|Quotation

<hr/>
<div style="display:none">
<jsp:include page="/pages/actionbutton.jsp"></jsp:include>
</div>