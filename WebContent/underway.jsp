<%@ page import="com.opensymphony.workflow.Workflow,
                 com.opensymphony.workflow.basic.BasicWorkflow,
                 java.util.*,
                 com.opensymphony.workflow.query.WorkflowQuery"%>
<% response.setHeader("Pragma","no-cache");
  response.setDateHeader("Expires",0);
  response.setHeader("Cache-Control","no-cache");
%>                 
Workflow Name:<%= (String)session.getAttribute("username") %><br/>
<%try{
     Workflow wf = new BasicWorkflow((String) session.getAttribute("username"));
     WorkflowQuery queryLeft = new WorkflowQuery(WorkflowQuery.OWNER, WorkflowQuery.CURRENT, WorkflowQuery.EQUALS, session.getAttribute("username"));
     WorkflowQuery queryRight = new WorkflowQuery(WorkflowQuery.STATUS, WorkflowQuery.CURRENT, WorkflowQuery.EQUALS, "Queued");
     WorkflowQuery query = new WorkflowQuery(queryLeft, WorkflowQuery.AND, queryRight);
     List workflows = wf.query(query);
    for (Iterator iterator = workflows.iterator(); iterator.hasNext();) {
        Long wfId = (Long) iterator.next();
%>
    <li><a href="wflview.jsp?id=<%= wfId %>"><%= wfId %></a></li>
<%
    }
}catch(Exception e ){System.out.println(e);}
%>

<%@ include file="nav.jsp" %>