<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>      
 
<script>
function submitactivity(){
alert(document.getElementById("submitanchor").href);
	location.href = document.getElementById("submitanchor").href;
	
}
</script>
 
<% String url=response.encodeURL(request.getContextPath()+"/WorkflowController.do"); %>
<a id="submitanchor" href="<%=url %>?action=true&do=${sessionScope.applicationDTO.currentActionId}"+>${sessionScope.applicationDTO.currentAction}</a>
 