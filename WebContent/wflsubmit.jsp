<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>  
<% response.setHeader("Pragma","no-cache");
  response.setDateHeader("Expires",0);
  response.setHeader("Cache-Control","no-cache");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
Workflow Session Name:<%String sessionName= (String) session.getAttribute("workflowSessName"); %><%=sessionName %><br/>
WorkflowId:<%String wflId = (String)Long.toString((Long) session.getAttribute("wflId")); %><%=wflId %><br/>
Workflow Stage:<%= (String) session.getAttribute("username") %><br/>
Workflow Next Stage:<%= (String) session.getAttribute("username") %><br/>
<% String url=response.encodeURL("WorkflowController.do"); %>
<a href="<%=url %>?action=true&id=<%=wflId %>&workflowSessName=<%=sessionName %>" >Start</a>
<c:out value="${workflowSessName }"/>
<c:forEach var="action" items="${hmActions}">
 <a href="<%=url %>?action=true&workflowSessName=<%=sessionName %>&id=<%=wflId %>&do=<c:out value="${action.key}"/>" ><c:out value="${action.value}" /></a>
 <c:out value="${action.key}"/> : <c:out value="${action.value}" /><br>
 
</c:forEach>
</body>
</html>