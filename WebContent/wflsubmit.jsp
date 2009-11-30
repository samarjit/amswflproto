<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>  
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
 

<% String url=response.encodeURL("WorkflowController.do"); %>
<%String context = request.getContextPath(); %>
<jsp:include page="/pages/wflproto.jsp"></jsp:include>
 
<c:forEach var="action" items="${hmActions}">
 <a id="submitanchor" href="<%=url %>?action=true&doString=${action.value}&wflid=${applicationDTO.wflid}"+>${action.key}</a> 
 <c:out value="${action.key}"/> : <c:out value="${action.value}" /><br>
 
</c:forEach>

action button:<br/>
<jsp:include page="/pages/actionbutton.jsp"></jsp:include>

</body>
</html>