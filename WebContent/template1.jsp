<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>  
<%@ page import="java.util.Iterator,java.util.LinkedHashMap" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<%String ctxstr = request.getContextPath(); %>
<style>
@import "<%=ctxstr %>/css/button.css";
@import "<%=ctxstr %>/css/header.css";
</style>
<script language="JavaScript" src="<%=ctxstr %>/js/commonjs.js"></script>

<s:property value="jsname" escape="false"/>
<s:url var="retriveurl" value="/retreivedetails.action" />
<s:url var="inserturl" value="/insertdata.action" />
<s:url var="updateurl" value="/updatedata.action" />
<s:url var="deleteurl" value="/deletedata.action" />


<script language="javascript" >
var retriveurlpart='<s:property value="%{#retriveurl}"/>';
var inserturlpart='<s:property value="%{#inserturl}"/>';
var screenName= '<s:property value="%{#parameters.screenName}"/>';
var updateurlpart='<s:property value="%{#updateurl}"/>';
var deleteurlpart='<s:property value="%{#deleteurl}"/>';
var whereClause= '<s:property value="%{#parameters.panelFieldsWhereClause}"/>';
</script>

</head>
<body onload="populate()">
<%String ctxpath=request.getContextPath(); %>
<%@ include file="pages/header.jsp" %>
<div id="page">
<!-- The following part is filled using template and DB -->
<table> 
<tr>
<td>
<div id=panelsdiv > 
<s:property value="dataPanel" escape="false"/>
 <!-- Using Iterator -->
 <s:iterator  value="extraFields" >
 <s:set var="som" value="key" />
 <s:if test="#som != 'buttonPanel'">  
 <%--s:property value="key"/--%> <s:property value="value" escape="false"/>
 </s:if>
 </s:iterator>
 </div>
</td>

<td> <s:property value="extraFields.buttonPanel" escape="false"/>
&nbsp;
</td>
</tr>
</table>

<!-- Alternate display of extra fields -->
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

<script>
function toggle(objthis){
	if(document.getElementById("retreivedetailsdiv").style.display == "none"){
		document.getElementById("retreivedetailsdiv").style.display = "block";
		objthis.innerHTML = "-";
	}else{
		document.getElementById("retreivedetailsdiv").style.display = "none";
		objthis.innerHTML = "+";
	}
}
</script>
<button onclick="toggle(this)">+</button>
<div id="retreivedetailsdiv" style="display:none">
</div>
</div> <!-- id = page -->
</body>
</html>