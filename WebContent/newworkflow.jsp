<%@ page import="com.opensymphony.workflow.Workflow,
                 com.opensymphony.workflow.basic.BasicWorkflow"%>
<% response.setHeader("Pragma","no-cache");
  response.setDateHeader("Expires",0);
  response.setHeader("Cache-Control","no-cache");
%>
<%if( session.getAttribute("username") == null || "".equals(session.getAttribute("username")))
	if(request.getParameter("username") != null && !"".equals(request.getParameter("username")))
	{
		session.setAttribute("username",request.getParameter("username"));
	}
    Workflow wf = new BasicWorkflow((String) session.getAttribute("username"));
    long id = wf.initialize("dm",0, null);
%>
Workflow Name:<%= (String)session.getAttribute("username") %><br/>
New workflow entry <a href="wflview.jsp?id=<%=id%>">#<%=id%></a> created and initialized!

<%@ include file="nav.jsp" %>