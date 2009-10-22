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

<div>The following action tag will execute result and include it in this page</div>
<br />
<s:action name="template1" executeResult="true" />

<div>The following action tag will execute result and include it in this page</div>
<br />

<br />
<div>The following action tag will do the same as above, but invokes method specialMethod in action</div>
<br />

<br />
<div>The following action tag will not execute result, but put a String in request scope
     under an id "stringByAction" which will be retrieved using property tag</div>
<s:action name="actionTagAction1!default" executeResult="false" />
<s:property value="#attr.stringByAction" />




</body>
</html>