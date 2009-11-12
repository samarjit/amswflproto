<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>  
<%@ taglib uri="/struts-dojo-tags" prefix="sx" %>
<%@ page import="java.util.Iterator,java.util.LinkedHashMap" %>  

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New Page</title>
<style>
@import "/css/button.css";
</style>
</head>
<script language="JavaScript" src="js/commonjs.js"></script>
<s:property value="jsname" escape="false"/>

<s:url var="url" value="/searchlist.action" />
<script language="javascript" >
var urlpart='<s:property value="%{#url}"/>';
var screenName= '<s:property value="%{#parameters.screenName}"/>' ;
</script>
<body>
template 2
 <s:property value="screenName"/> 
 
<table> 
<tr>
<td>
<s:property value="dataPanel" escape="false"/>
 Using Iterator
 <s:iterator  value="extraFields" >
 <s:set var="som" value="key" />
 <s:if test="#som != 'buttonPanel' && #som != 'searchPanel'">  
 <div id='<s:property value="key"/>'> <s:property value="key"/><s:property value="value" escape="false"/></div>
 </s:if>
 </s:iterator>
</td>

<td> <s:property value="extraFields.buttonPanel" escape="false"/>
&nbsp;
</td>
</tr>
</table>


<form action="template1.action" >
<select name="screenName" >
<option >frmRequest</option>
<option >frmRequestList</option> 
</select>
<input type="hidden" id="panelFieldsWhereClause" name="panelFieldsWhereClause" value="">
<input type="submit" value="view" onclick="return makeWhereClause()" />
<input type="submit" value="Create Request" onclick="clearWhereClause()"/>
</form>


<input type="button" onclick="search()" value="send ajax" />
<div id="searchdiv"></div>
 
<sx:datetimepicker name="extraFields.buttonPanel" label="Order Date" />

</body>
</html>