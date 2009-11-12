<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
<link rel="stylesheet" href="../css/login.css" type="text/css" />
<STYLE>
@import "../css/button.css";
</STYLE>
</head>
<SCRIPT LANGUAGE="JavaScript">
function fnsubmit() {
document.getElementById("formId0").submit();
}
</SCRIPT>
<body>

<br/>
<FORM METHOD="GET" ACTION="login.action" id="formId0">
<input type="hidden" name="scrflowname" value="loginflow"/>
<input type="hidden" name="sceenname" value="login"/> 
<div id="itsthetable" style="width:auto"> 
<TABLE id="login">
<thead>
<TR><TH colspan="2" >Login</TH></TR></thead>
<tr><td>User ID</td><td><input type="text" name="username"  /></td></tr>
<tr><td>Password </td><td>
<input type="text" value="" name="password" /> </td></tr>
<tr><td><a href="#" class="button"   onclick="javascript:document.forms[0].reset();this.blur();" ><span>Reset</span></a> 
</td><td><a href="#" class="button"   onclick="fnsubmit();this.blur();" ><span>Login</span></a></td></tr>
</TABLE> 
</div>
</FORM>

|<a href ="${pageContext.servletContext.contextPath}/index2.jsp">index2 for debug</a>|
</body>
</html>