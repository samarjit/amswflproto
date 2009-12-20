<%@ page import="com.opensymphony.workflow.Workflow,
                 com.opensymphony.workflow.basic.BasicWorkflow,
                 com.opensymphony.workflow.spi.Step,
                 java.util.List" %>
<% response.setHeader("Pragma","no-cache");
  response.setDateHeader("Expires",0);
  response.setHeader("Cache-Control","no-cache");
%>


 
<html>
<body>
<form action="#" method="GET">
ActionDesc:<input name="actiondesc" value="<%= request.getParameter("actiondesc") %>"/>
xAdjust:<input name="xadjust" value="-3"/>
yAdjust:<input name="yadjust" value="-2"/>
<input type="submit" />
</form>

<div id="workflowCanvas" style="position:relative;height:566px;width:508px;">
<img src="processimage.jpg" border=0/>
</div>
<script type="text/javascript" src="js/wz_jsgraphics.js"></script>
<script type="text/javascript" src="js/xmlextras.js"></script>
<script type="text/javascript">
var actiondesc = "<%=request.getParameter("actiondesc") %>";

var xmlHttp = XmlHttp.create();
var async = true;
xmlHttp.open("GET", ".gpd.newworkflow.xml", async);
xmlHttp.onreadystatechange = function () {
    if (xmlHttp.readyState == 4){
        //set up graphics
        var jg = new jsGraphics("workflowCanvas");
        jg.setColor("#ff0000");
        jg.setStroke(3);
       /* var xAdjust = 68;
        var yAdjust = -17;
        var widthAdjust = 3;
        var heightAdjust = 2;*/
        var xAdjust = -3;
        <%if(null != request.getParameter("xadjust")){%>
         xAdjust = <%=request.getParameter("xadjust") %>;
         document.getElementsByName("xadjust")[0].value=xAdjust;
        <%} %>
      
        var yAdjust = -2;
        <% if(null != request.getParameter("yadjust")){%>
         yAdjust = <%=request.getParameter("yadjust") %>;
         document.getElementsByName("yadjust")[0].value=yAdjust;
        <%} %>
         
         
        var widthAdjust = 1;
        var heightAdjust = 1;
        //parsing xml and paint;
        var cells = xmlHttp.responseXML.getElementsByTagName("node");
        //for(var i = 0; i < currentStepIds.length; i++)
            {
            for(var n = 0; n < cells.length; n++){
                var cell = cells[n];//alert(cell.getAttribute("name")+" "+ actiondesc);
                if(cell.getAttribute("name") == actiondesc )
                    {
                    //alert(cell.getAttribute("x") + " " +cell.getAttribute("y")+" "+  cell.getAttribute("width")   +" "+  cell.getAttribute("height") );
                    jg.drawRect(parseInt(cell.getAttribute("x")) + xAdjust, parseInt(cell.getAttribute("y")) + yAdjust, parseInt(cell.getAttribute("width")) + widthAdjust, parseInt(cell.getAttribute("height")) + heightAdjust);
                }
            }
        }
        jg.paint();
    }
};
xmlHttp.send(null);    
</script>

</body>
</html>