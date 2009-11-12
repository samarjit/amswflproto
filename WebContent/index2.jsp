<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="s" uri="/struts-tags" %>    

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
Final 
<%-- s:property value="%{#parameter.errorMessage}" /--%>

<% String tplErr= (String)request.getAttribute("errorMessage");
if(tplErr != null && ! "".equalsIgnoreCase(tplErr)){
	out.println("<div style='color:RED'>"+tplErr+"</div>");
}
%>

<a href="http://localhost:8080/generatehtml/dynamic.action">to call dynamic action</a>
<a href="http://localhost:8080/generatehtml/sampleAction.action">to call action</a>
<a href="http://localhost:8080/generatehtml/template1.action">to template1</a>
<form action="template1.action">
<select name="screenName" >
<option >frmRequest</option>
<option >frmRequestList</option> 
</select>
<input type="submit" />
</form>

<input type="text" value="newuser" name="username" id="username"/>
<a href="#" onclick="javascript:location.href='newworkflow.jsp?username='+document.getElementById('username').value" >new workflow</a>

<form action="WorkflowController.do" method="GET">
WorkflowSession Name:<input name="workflowSessName" /><br/>
 <input name="create" value="true"/><br/>
<input type="submit" />
</form>

<a href="pages/login.jsp" >workflow proto</a>



</body>
</html>