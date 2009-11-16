<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<title>Struts 2 Login Application!</title>



</head>
<body>
<s:form action="sampleAction.action" method="POST">
<tr>
<td colspan="2">
Login
</td>

</tr>

  <tr>
   <td colspan="2">
         <s:actionerror />
         <s:fielderror />
   </td>
  </tr>

<s:textfield name="user" label="Login name"/>
<s:password name="password" label="Password"/>
<s:submit value="Login" align="center"/>

</s:form>

</body>

</html>