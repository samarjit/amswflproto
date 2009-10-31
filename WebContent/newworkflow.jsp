<%@ page import="com.opensymphony.workflow.Workflow,
                 com.opensymphony.workflow.basic.BasicWorkflow,
                 dto.WflViewDTO"%>
<% response.setHeader("Pragma","no-cache");
  response.setDateHeader("Expires",0);
  response.setHeader("Cache-Control","no-cache");
%>
<%
WflViewDTO wflviewDTO = (WflViewDTO)session.getAttribute("wflviewdto");
String wflsessionname = wflviewDTO.getWflsessionname();
    
	Workflow wf = new BasicWorkflow(wflsessionname);
    long id = wf.initialize("newwfl",0, null);
%>
Workflow Name:<%= (String)session.getAttribute("username") %><br/>
New workflow entry <a href="wflview.jsp?id=<%=id%>">#<%=id%></a> created and initialized!

<%@ include file="nav.jsp" %>