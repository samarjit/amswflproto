<%@ page import="com.opensymphony.workflow.Workflow,
                 com.opensymphony.workflow.basic.BasicWorkflow,
                 java.util.*,
                 com.opensymphony.workflow.query.WorkflowQuery,
                 dto.WflViewDTO"%>
<% response.setHeader("Pragma","no-cache");
  response.setDateHeader("Expires",0);
  response.setHeader("Cache-Control","no-cache");
%>          

<%

WflViewDTO wflviewDTO = (WflViewDTO)session.getAttribute("wflviewdto");
String wflsessionname = wflviewDTO.getWflsessionname();
%>       
Workflow Name:<%= wflsessionname %><br/>
<%try{
     Workflow wf = new BasicWorkflow(wflsessionname);
     WorkflowQuery queryLeft = new WorkflowQuery(WorkflowQuery.CALLER, WorkflowQuery.CURRENT, WorkflowQuery.EQUALS,"");
     WorkflowQuery queryRight1 = new WorkflowQuery(WorkflowQuery.STATUS, WorkflowQuery.CURRENT, WorkflowQuery.EQUALS, "Queued");
     WorkflowQuery queryRight2 = new WorkflowQuery(WorkflowQuery.STATUS, WorkflowQuery.CURRENT, WorkflowQuery.EQUALS, "Underway");
     WorkflowQuery queryRight = new WorkflowQuery(queryRight1, WorkflowQuery.OR, queryRight2);
     WorkflowQuery query = new WorkflowQuery(queryLeft, WorkflowQuery.AND, queryRight2);
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