<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
  <head>
    <title>Action Tag Example!</title>
  </head>
  <body>
    <h1><span style="background-color: #FFFFcc">Action Tag 
                        (Data Tags) Example!</span></h1>
    <s:action name="success"  executeResult="false" >
        <b><i>The action tag will execute the result and include 
                            it in this page.</i></b>
      </s:action>
  </body>
</html>