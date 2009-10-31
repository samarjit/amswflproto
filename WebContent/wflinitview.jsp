<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="dto.WflViewDTO" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
WflViewDTO wflviewDTO = (WflViewDTO)session.getAttribute("wflviewdto");
String wflsessionname ="";
String wflid = "";
boolean flagchanged = false;

if(wflviewDTO != null){
wflsessionname= wflviewDTO.getWflsessionname();
wflid=wflviewDTO.getWflid();
}

if(request.getParameter("wflsessionname") != null){
wflsessionname = request.getParameter("wflsessionname");
flagchanged = true;
}

if(request.getParameter("wflid") != null){
wflid = request.getParameter("wflid");
flagchanged = true;
}

if(flagchanged ){
if(wflviewDTO == null )wflviewDTO = new WflViewDTO();
wflviewDTO.setWflsessionname(wflsessionname);
wflviewDTO.setWflid(wflid);
session.setAttribute("wflviewdto",wflviewDTO);
}
%>
<FORM METHOD="POST" ACTION="wflinitview.jsp">
Workflow Session Name:<input type="text" name="wflsessionname" value="<%=wflsessionname %>" /><br/>
Workflow Id: <input type="text" name="wflid"  value="<%=wflid %>"  /> 
<INPUT TYPE="submit">
</FORM>

<jsp:include page="nav.jsp"></jsp:include>
</body>
</html>