<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>  
<%@ page import="java.util.Iterator,java.util.LinkedHashMap" %>  

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%String ctxstr = request.getContextPath(); %>
<title>New Page</title>
<style>
@import "<%=ctxstr %>/css/button.css";
@import "<%=ctxstr %>/css/header.css"; 
</style>
</head>
<script language="JavaScript" src="<%=ctxstr %>/js/commonjs.js"></script>
<s:property value="jsname" escape="false"/>

<s:url var="url" value="/searchlist.action" />
<script language="javascript" >
var urlpart='<s:property value="%{#url}"/>';
var screenName= '<s:property value="%{#parameters.screenName}"/>' ;
</script>
<body>
<%@ include file="pages/header.jsp" %>
<div id="page">
 
<table> 
<tr>
<td>
<s:property value="dataPanel" escape="false"/>

 <s:iterator  value="extraFields" >
 <s:set var="som" value="key" />
 <s:if test="#som != 'buttonPanel' && #som == 'searchPanel'">  
 <div id='<s:property value="key"/>'> <%--s:property value="key"/--%><s:property value="value" escape="false"/></div>
 </s:if>
 </s:iterator>
</td>

<td> <s:property value="extraFields.buttonPanel" escape="false"/>
&nbsp;
</td>
</tr>
</table>


<form action="template1.action" id="formwhere" style="display:none">
<%-- select name="screenName" >
<option >frmRequest</option>
<option >frmRequestList</option> 
</select --%>
<input type="hidden" name="screenName" id="screenName" />
<input type="hidden" id="panelFieldsWhereClause" name="panelFieldsWhereClause" value="">
<input type="submit" value="view" onclick="return viewdetails()" />
<input type="submit" value="Create Request" onclick="clearWhereClause()"/>
</form>


<%--input type="button" onclick="search()" value="send ajax" />
<div class="clear" ><a href="#" class="button" name='search' id='search'    onclick="search();" ><SPAN>Search</SPAN></a></div--%>
<div id="searchdiv"></div>
 
</div> <!-- id = page -->
</body>
</html>