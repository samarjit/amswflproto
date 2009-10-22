<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>  
<%@ page import="java.util.Iterator,java.util.LinkedHashMap" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script language="JavaScript" src="js/commonjs.js"></script>
<script language="javascript" >
function sendReq(){
	sendAjaxGet(null,mycall);
}
function mycall(p){
	alert("Got from ajax:"+p);
}
</script>
</head>
<body>
The following part is filled using template and DB
<table> 
<tr>
<td>
<s:property value="dataPanel" escape="false"/>
 Using Iterator
 <s:iterator  value="extraFields" >
 <s:set var="som" value="key" />
 <s:if test="#som != 'buttonPanel'">  
 <s:property value="key"/> <s:property value="value" escape="false"/>
 </s:if>
 </s:iterator>
</td>

<td> <s:property value="extraFields.buttonPanel" escape="false"/>
&nbsp;
</td>
</tr>


</table>

Extra fields:
<table> 
<tr>
<td>
 
<%
LinkedHashMap hm =(LinkedHashMap)( request.getAttribute("extraFields"));
 Iterator i = (Iterator)(hm).keySet().iterator();
%>
 


<%-- while(i.hasNext()) {
     String s = (String) i.next(); %>
      <%if(!s.equalsIgnoreCase("buttonPanel")){ %>  
     <%=s%>  <%=hm.get(s)%>
     <%}%>
       <BR/>
<%    }
--%>

</td>
</tr>
</table>

 

</body>
</html>